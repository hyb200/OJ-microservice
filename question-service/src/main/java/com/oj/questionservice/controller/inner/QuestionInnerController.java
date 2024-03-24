package com.oj.questionservice.controller.inner;

import com.oj.backendmodel.model.entity.Question;
import com.oj.backendmodel.model.entity.QuestionSubmit;
import com.oj.questionservice.mapper.QuestionMapper;
import com.oj.questionservice.service.QuestionService;
import com.oj.questionservice.service.QuestionSubmitService;
import com.oj.serviceclient.service.QuestionFeignClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/inner")
public class QuestionInnerController implements QuestionFeignClient {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private QuestionMapper questionMapper;

    @GetMapping("/get/id")
    @Override
    public Question getQustionById(@RequestParam("questionId") long questionId) {
        return questionService.getById(questionId);
    }

    @GetMapping("/update/sn")
    @Override
    public void increaseSubmitNumByQuestionId(@RequestParam("questionId") long questionId) {
        questionMapper.increaseSubmitNum(questionId);
    }

    @GetMapping("/update/an")
    @Override
    public void increaseAcceptedNumByQuestionId(@RequestParam("questionId") long questionId) {
        questionMapper.increaseAcceptedNum(questionId);
    }

    @GetMapping("/submit/get/id")
    @Override
    public QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") long questionSubmitId) {
        return questionSubmitService.getById(questionSubmitId);
    }

    @PostMapping("/submit/update")
    @Override
    public boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit) {
        return questionSubmitService.updateById(questionSubmit);
    }
}
