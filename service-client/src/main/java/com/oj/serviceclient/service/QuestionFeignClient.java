package com.oj.serviceclient.service;

import com.oj.backendmodel.model.entity.Question;
import com.oj.backendmodel.model.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
* @author zxc13
* @description 针对表【question(题目)】的数据库操作Service
* @createDate 2023-12-07 11:25:04
*/
@FeignClient(name = "question-service", path = "/api/question/inner")
public interface QuestionFeignClient {

    @GetMapping("/get/id")
    Question getQustionById(@RequestParam("questionId") long questionId);

    @GetMapping("/update/sn")
    void increaseSubmitNumByQuestionId(@RequestParam("questionId") long questionId);

    @GetMapping("/update/an")
    void increaseAcceptedNumByQuestionId(@RequestParam("questionId") long questionId);

    @GetMapping("/submit/get/id")
    QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") long questionSubmitId);

    @PostMapping("/submit/update")
    boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit);
}
