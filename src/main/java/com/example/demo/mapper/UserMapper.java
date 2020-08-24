package com.example.demo.mapper;

import com.example.demo.model.UserDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    @Insert("insert into user(name, access_id,token,gmt_create,gmt_modified) values (#{name},#{accessId},#{token},#{gmtCreate},#{gmtModified})")
    void insert(UserDO user);
}
