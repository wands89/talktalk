package com.study.service.entity;

import lombok.Data;

@Data
public class UcUserInfo {
    private Integer id;
    private String userName;
    private String nickName;
    private String password;
    private String role;
}
