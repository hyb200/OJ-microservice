package com.oj.judgeservice.judge.codesandbox.impl;


import com.oj.backendmodel.model.codesandbox.ExecuteCodeRequest;
import com.oj.backendmodel.model.codesandbox.ExecuteCodeResponse;
import com.oj.backendmodel.model.codesandbox.JudgeInfo;
import com.oj.backendmodel.model.enums.JudgeInfoMessageEnum;
import com.oj.backendmodel.model.enums.QuestionSubmitStatusEnum;
import com.oj.judgeservice.judge.codesandbox.CodeSandBox;

import java.util.List;

/**
 * 示例代码沙箱（为了跑通业务流程）
 */
public class ExampleCodeSandboxImpl implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCodeResponse(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputs = executeCodeRequest.getInputs();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();

        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getValue());
        judgeInfo.setTime(100l);
        judgeInfo.setMemory(100l);

        executeCodeResponse.setOutputs(inputs);
        executeCodeResponse.setMessage("执行成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        executeCodeResponse.setJudgeInfo(judgeInfo);

        return executeCodeResponse;
    }
}
