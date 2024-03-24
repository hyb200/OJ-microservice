package com.oj.judgeservice.judge.codesandbox;


import com.oj.judgeservice.judge.codesandbox.impl.ExampleCodeSandboxImpl;
import com.oj.judgeservice.judge.codesandbox.impl.RemoteCodeSandboxImpl;
import com.oj.judgeservice.judge.codesandbox.impl.ThirdPartyCodeSandboxImpl;

/**
 * 代码沙箱工厂
 */
public class CodeSandboxFactory {

    /**
     * 根据字符串参数创建对应的沙箱示例
     * @param type
     * @return
     */
    public static CodeSandBox createCodeSandboxInstance(String type) {
        switch (type) {
            case "example":
                return new ExampleCodeSandboxImpl();
            case "remote":
                return new RemoteCodeSandboxImpl();
            case "thirdParty":
                return new ThirdPartyCodeSandboxImpl();
            default:
                return new ExampleCodeSandboxImpl();
        }
    }
}
