package com.study.service.properties;

import lombok.Data;

@Data
public class ValidateCodeProperties {

    private ImageCodeProperties image=new ImageCodeProperties();
    private SmsCodeProperties sms=new SmsCodeProperties();

}
