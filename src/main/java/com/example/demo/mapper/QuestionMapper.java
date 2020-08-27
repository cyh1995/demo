package com.example.demo.mapper;

import com.example.demo.model.QuestionDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(QuestionDO question);

    @Select("select * from question")
    List<QuestionDO> selectQuestion();
}
