package com.oj.questionservice;

import com.oj.questionservice.manager.RedisLimiterManager;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class QuestionServiceApplicationTests {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private RedisLimiterManager redisLimiterManager;

    @Test
    void contextLoads() {
        RBucket<String> bucket = redissonClient.getBucket("k1");
        bucket.set("v1");
    }

    @Test
    void testRateLimiter() {
        String key = "ikun";
        for (int i = 0; i < 5; i++) {
            redisLimiterManager.doRateLimit(key);
            System.out.println("success");
        }

    }

}
