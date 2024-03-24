package com.oj.backendmodel.model.codesandbox;

import lombok.Data;

/**
 * 判题信息
 */
@Data
public class JudgeInfo {
    /**
     * 程序执行信息
     */
    private String message;

    /**
     * 执行耗时 -- ms
     */
    private long time;

    /**
     * 内存消耗 -- KB
     */
    private long memory;
}
