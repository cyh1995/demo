package com.example.demo.mapper;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.model.QuestionDO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(QuestionDO question);

    @Select("select * from question limit #{offset}, #{size}")
    List<QuestionDO> selectQuestion(@Param(value = "offset") Integer offset, @Param(value = "size")Integer size);

    @Select("select count(1) from question")
    int getPageCount();

    @Select("select * from question where creator = #{userId} limit #{offset}, #{size}")
    List<QuestionDO> findQuestionById(@Param(value = "userId")Integer userId,@Param(value = "offset") Integer offset, @Param(value = "size")Integer size);

    @Select("select count(1) from question where creator = #{userId}")
    int getPageCountByUserId(@Param(value = "userId")Integer userId);

    @Select("select * from question where id = #{questionId}")
    QuestionDTO getQuestionById(Integer questionId);

    @Update("update question set title = #{title},description = #{description},tag = #{tag} where id = #{id}")
    void update(QuestionDO question);
}
