package com.oj.questionservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oj.backendmodel.model.entity.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
* @author zxc13
* @description 针对表【question(题目)】的数据库操作Mapper
* @createDate 2023-12-07 11:25:04
* @Entity com.oj.model.entity.Question
*/
public interface QuestionMapper extends BaseMapper<Question> {
    @Update("update question set submitNum = submitNum + 1 where id = #{id};")
    void increaseSubmitNum(@Param("id") long id);

    @Update("update question set acceptedNum = acceptedNum + 1 where id = #{id};")
    void increaseAcceptedNum(@Param("id") long id);
}




