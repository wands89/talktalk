package com.study.service.authentication.code.sms;

public interface SmsCodeSender {
    void send(String mobile,String code);

}
