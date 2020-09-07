package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
@Autowired
    private UserMapper userMapper;

    public void creatOrUpdate(UserDO userDO) {

        UserDO userDB = userMapper.findByAccessId(userDO.getAccessId());
        if(userDB==null){
            userDO.setGmtCreate(System.currentTimeMillis());
            userDO.setGmtModified(userDO.getGmtCreate());
            userMapper.insert(userDO);
        }
       else{
            userDB.setToken(userDO.getToken());
            userDB.setName(userDO.getName());
            userDB.setAvatarUrl(userDO.getAvatarUrl());
            userDB.setGmtModified(userDO.getGmtCreate());
            userMapper.update(userDB);
        }
    }
}
