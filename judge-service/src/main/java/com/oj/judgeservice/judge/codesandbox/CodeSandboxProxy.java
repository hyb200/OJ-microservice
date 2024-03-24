package com.oj.judgeservice.judge.codesandbox;

import com.oj.backendmodel.model.codesandbox.ExecuteCodeRequest;
import com.oj.backendmodel.model.codesandbox.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CodeSandboxProxy implements CodeSandBox{

    private final CodeSandBox codeSandBox;

    public CodeSandboxProxy(CodeSandBox codeSandBox) {
        this.codeSandBox = codeSandBox;
    }

    @Override
    public ExecuteCodeResponse executeCodeResponse(ExecuteCodeRequest executeCodeRequest) {
        log.info("沙箱请求信息： " + executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCodeResponse(executeCodeRequest);
        log.info("沙箱执行结果： " + executeCodeResponse.toString());
        return executeCodeResponse;
    }
}
