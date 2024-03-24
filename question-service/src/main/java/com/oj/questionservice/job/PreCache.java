package com.oj.questionservice.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oj.backendmodel.model.entity.Question;
import com.oj.questionservice.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 缓存预热定时任务
 */
@Component
@Slf4j
public class PreCache {

    @Resource
    private QuestionService questionService;

    @Resource
    private RedissonClient redissonClient;

    @Scheduled(cron = "0 0 0 * * *")
    public void preCache() {
        RLock lock = redissonClient.getLock("oj:question:precache:lock");
        try {
            if (lock.tryLock(0, -1, TimeUnit.MILLISECONDS)) {
                String redisKey = String.format("oj:question:1") ;

                RBucket<Page<Question>> bucket = redissonClient.getBucket(redisKey);
                Page<Question> questionPage = questionService.page(new Page<>(1, 10), new QueryWrapper<>());
                //  将查询结果写入缓存
                try {
                    bucket.set(questionPage, 30000, TimeUnit.MILLISECONDS);
                } catch (Exception e) {
                    log.error("redis set key error", e);
                }
            }
        } catch (InterruptedException e) {
            log.error("preCache error", e);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                log.info("unlock: " + Thread.currentThread().getName());
                lock.unlock();
            }
        }
    }
}
