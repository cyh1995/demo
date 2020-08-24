package com.example.demo.model;

import lombok.Data;

@Data
public class UserDO {
    private String id;
    private String accessId;
    private String name;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;

}
