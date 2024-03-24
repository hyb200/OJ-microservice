package com.oj.judgeservice.judge.codesandbox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.oj.backendcommon.common.ErrorCode;
import com.oj.backendcommon.exception.BusinessException;
import com.oj.backendmodel.model.codesandbox.ExecuteCodeRequest;
import com.oj.backendmodel.model.codesandbox.ExecuteCodeResponse;
import com.oj.judgeservice.judge.codesandbox.CodeSandBox;
import org.apache.commons.lang3.StringUtils;

/**
 * 远程代码沙箱
 */
public class RemoteCodeSandboxImpl implements CodeSandBox {

    public static final String AUTH_REQUEST_HEADER = "auth";

    public static final String AUTH_REQUEST_SECERT = "secertKey";
    @Override
    public ExecuteCodeResponse executeCodeResponse(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        String url = "http://192.168.5.130:8112/executeCode";
        String body = JSONUtil.toJsonStr(executeCodeRequest);
        String response = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECERT)
                .body(body)
                .execute()
                .body();
        if (StringUtils.isBlank(response)) {
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "Remote Sandbox error. " + response);
        }
        return JSONUtil.toBean(response, ExecuteCodeResponse.class);
    }
}
