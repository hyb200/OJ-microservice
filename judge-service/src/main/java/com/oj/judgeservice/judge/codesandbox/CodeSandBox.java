package com.oj.judgeservice.judge.codesandbox;


import com.oj.backendmodel.model.codesandbox.ExecuteCodeRequest;
import com.oj.backendmodel.model.codesandbox.ExecuteCodeResponse;

public interface CodeSandBox {

    ExecuteCodeResponse executeCodeResponse(ExecuteCodeRequest executeCodeRequest);
}
