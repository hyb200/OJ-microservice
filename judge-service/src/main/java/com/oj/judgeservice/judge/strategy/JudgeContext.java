package com.oj.judgeservice.judge.strategy;

import com.oj.backendmodel.model.codesandbox.JudgeInfo;
import com.oj.backendmodel.model.dto.question.JudgeCase;
import com.oj.backendmodel.model.entity.Question;
import com.oj.backendmodel.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 上下文（用于定义在策略中传递的参数）
 */
@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputs;

    private List<String> outputs;

    private List<JudgeCase> judgeCases;

    private Question question;

    private QuestionSubmit questionSubmit;
}
