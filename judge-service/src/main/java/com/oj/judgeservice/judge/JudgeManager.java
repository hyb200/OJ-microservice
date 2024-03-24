package com.oj.judgeservice.judge;

import com.oj.backendmodel.model.codesandbox.JudgeInfo;
import com.oj.backendmodel.model.entity.QuestionSubmit;
import com.oj.judgeservice.judge.strategy.DefaultJudgeStrategy;
import com.oj.judgeservice.judge.strategy.JavaLanguageJudgeStrategy;
import com.oj.judgeservice.judge.strategy.JudgeContext;
import com.oj.judgeservice.judge.strategy.JudgeStrategy;
import org.springframework.stereotype.Service;

@Service
public class JudgeManager {
    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy strategy = new DefaultJudgeStrategy();
        if (language.equals("java")) {
            strategy = new JavaLanguageJudgeStrategy();
        }
        return strategy.doJudge(judgeContext);
    }
}
