package com.example.demo.mapper;

import com.example.demo.model.UserDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.UUID;

@Mapper
public interface UserMapper {
    @Insert("insert into user(name, access_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accessId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(UserDO user);

    @Select("select * from user where token = #{token}")
    UserDO findByToken(String token);
    @Select("select * from user where id = #{id}")
    UserDO findById(Integer creator);
}
