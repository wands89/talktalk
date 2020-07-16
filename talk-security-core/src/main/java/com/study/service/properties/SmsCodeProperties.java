package com.study.service.properties;

import lombok.Data;

@Data
public class SmsCodeProperties {
    private Integer length=6;
    private Integer expiredIn=60;
    private String url;
}
