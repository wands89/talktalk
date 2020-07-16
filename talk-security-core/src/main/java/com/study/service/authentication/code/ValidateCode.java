package com.study.service.authentication.code;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 短信验证码
 */
@Data
public class ValidateCode {

    private String code;
    private LocalDateTime expireTime;

    public ValidateCode() {};

    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;

        this.expireTime = expireTime;
    }

    public ValidateCode(String code, Integer expireIn) {
        this.code = code;

        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public boolean isExpried() {
        return LocalDateTime.now().isAfter(expireTime);
    }

}
