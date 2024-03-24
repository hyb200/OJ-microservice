package com.oj.judgeservice.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.oj.backendmodel.model.codesandbox.JudgeInfo;
import com.oj.backendmodel.model.dto.question.JudgeCase;
import com.oj.backendmodel.model.dto.question.JudgeConfig;
import com.oj.backendmodel.model.entity.Question;
import com.oj.backendmodel.model.enums.JudgeInfoMessageEnum;

import java.util.List;

/**
 * 默认判题策略
 */
public class DefaultJudgeStrategy implements JudgeStrategy {

    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {

        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        long time = judgeInfo.getTime();
        long memory = judgeInfo.getMemory();
        List<String> inputs = judgeContext.getInputs();
        List<String> outputs = judgeContext.getOutputs();
        Question question = judgeContext.getQuestion();
        List<JudgeCase> judgeCases = judgeContext.getJudgeCases();

        JudgeInfo judgeInfoResponse = new JudgeInfo();
        judgeInfoResponse.setTime(time);
        judgeInfoResponse.setMemory(memory);
        //  3.根据沙箱的执行结果，设置题目的判题状态和信息
        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.ACCEPTED;
        //  判断沙箱执行的结果输出数量和预期输出数量是否相等
        if (outputs.size() != inputs.size()) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        //  依次判断每一项输出和预期输出是否相等
        for (int i = 0; i < judgeCases.size(); i++) {
            JudgeCase judgeCase = judgeCases.get(i);
            if (!judgeCase.getOutput().equals(outputs.get(i))) {
                judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
                judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
                return judgeInfoResponse;
            }
        }
        //  判断题目限制
        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        long timeLimit = judgeConfig.getTimeLimit();
        long memoryLimit = judgeConfig.getMemoryLimit();

        if (time > timeLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        if (memory > memoryLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }

        judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
        return judgeInfoResponse;
    }
}
