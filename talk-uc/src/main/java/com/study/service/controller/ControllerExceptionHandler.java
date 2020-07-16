package com.study.service.controller;

import com.study.service.exception.UserNotExsitException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(UserNotExsitException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String,Object> handleUserNotException(UserNotExsitException e){
       Map<String,Object> result=new HashMap<>();
       result.put("id",e.getId());
       result.put("err",e.getMessage());
       return result;
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String,Object> handleExpiredJwtException(ExpiredJwtException e){
        Map<String,Object> result=new HashMap<>();
        result.put("err",e.getMessage());
        return result;
    }
}
