package com.oj.judgeservice.judge.codesandbox.impl;


import com.oj.backendmodel.model.codesandbox.ExecuteCodeRequest;
import com.oj.backendmodel.model.codesandbox.ExecuteCodeResponse;
import com.oj.judgeservice.judge.codesandbox.CodeSandBox;

/**
 * 第三方代码沙箱，调用网上的代码沙箱
 */
public class ThirdPartyCodeSandboxImpl implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCodeResponse(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
