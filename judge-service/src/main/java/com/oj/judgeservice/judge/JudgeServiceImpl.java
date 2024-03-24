package com.oj.judgeservice.judge;

import cn.hutool.json.JSONUtil;
import com.oj.backendcommon.common.ErrorCode;
import com.oj.backendcommon.exception.BusinessException;
import com.oj.backendmodel.model.codesandbox.ExecuteCodeRequest;
import com.oj.backendmodel.model.codesandbox.ExecuteCodeResponse;
import com.oj.backendmodel.model.codesandbox.JudgeInfo;
import com.oj.backendmodel.model.dto.question.JudgeCase;
import com.oj.backendmodel.model.entity.Question;
import com.oj.backendmodel.model.entity.QuestionSubmit;
import com.oj.backendmodel.model.enums.JudgeInfoMessageEnum;
import com.oj.backendmodel.model.enums.QuestionSubmitStatusEnum;
import com.oj.judgeservice.judge.codesandbox.CodeSandBox;
import com.oj.judgeservice.judge.codesandbox.CodeSandboxFactory;
import com.oj.judgeservice.judge.codesandbox.CodeSandboxProxy;
import com.oj.judgeservice.judge.strategy.JudgeContext;
import com.oj.serviceclient.service.QuestionFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionFeignClient questionFeignClient;

    @Value("${codesandbox.type: example}")
    private String type;

    @Resource
    private JudgeManager judgeManager;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        //  传入题目的提交id，获取到对应的题目、提交信息
        QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }

        long questionId = questionSubmit.getQuestionId();
        Question question = questionFeignClient.getQustionById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }

        //  如果不在等待状态，就不用重复执行
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.PENDING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目判题中");
        }

        //  更改判题状态为“判题中”，防止重复执行
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.JUDGING.getValue());

        boolean update = questionFeignClient.updateQuestionSubmitById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "判题状态更新失败");
        }

        questionFeignClient.increaseSubmitNumByQuestionId(questionId);
        //  调用沙箱，获取到执行结果
        CodeSandBox codeSandBox = CodeSandboxFactory.createCodeSandboxInstance(type);
        codeSandBox = new CodeSandboxProxy(codeSandBox);
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        //  获取输入用例
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCases = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputs = judgeCases.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputs(inputs)
                .build();

        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCodeResponse(executeCodeRequest);

        JudgeInfo judgeInfo = executeCodeResponse.getJudgeInfo();
        if (executeCodeResponse.getStatus() != 3) {
            //  根据沙箱的执行结果，设置题目的判题状态和信息
            List<String> outputs = executeCodeResponse.getOutputs();
            JudgeContext judgeContext = new JudgeContext();
            judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
            judgeContext.setInputs(inputs);
            judgeContext.setOutputs(outputs);
            judgeContext.setJudgeCases(judgeCases);
            judgeContext.setQuestion(question);
            judgeContext.setQuestionSubmit(questionSubmit);
            judgeInfo = judgeManager.doJudge(judgeContext);
        }

        if (judgeInfo.getMessage().equals(JudgeInfoMessageEnum.ACCEPTED.getValue())) {
            questionFeignClient.increaseAcceptedNumByQuestionId(questionId);
        }

        //  修改数据库中的判题结果
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionFeignClient.updateQuestionSubmitById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "判题状态更新失败");
        }

        QuestionSubmit questionSubmitResult = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        return questionSubmitResult;
    }
}
