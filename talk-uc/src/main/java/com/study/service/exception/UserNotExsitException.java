package com.study.service.exception;

import lombok.Data;

@Data
public class UserNotExsitException extends RuntimeException{

    private static final long serialVersionUID = 4156090161774489522L;

    private String id;
    public UserNotExsitException(String id){
        super("user not exsit!");
        this.id=id;

    }
}
