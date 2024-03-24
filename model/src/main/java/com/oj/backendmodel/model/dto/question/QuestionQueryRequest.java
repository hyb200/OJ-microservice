package com.oj.backendmodel.model.dto.question;

import com.oj.backendcommon.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 查询请求
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionQueryRequest extends PageRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表（json 数组）
     */
    private List<String> tags;

    /**
     * 题目难度
     */
    private String difficulty;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 是否搜索（决定是否走缓存）
     */
    private boolean isSearch;

    private static final long serialVersionUID = 1L;
}