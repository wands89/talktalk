package com.study.service.authentication.code;

import com.study.service.authentication.code.image.ImageCodeGenerator;
import com.study.service.authentication.code.sms.DefaultSmsCodeSender;
import com.study.service.authentication.code.sms.SmsCodeSender;
import com.study.service.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidateCodeBeanConfig {
    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    @ConditionalOnMissingBean(name="imageCodeGenerator")
    public ValidateCodeGenerator imageCodeGenerator(){
        ImageCodeGenerator codGenerator=new ImageCodeGenerator();
        codGenerator.setSecurityProperties(securityProperties);
        return codGenerator;
    }
    @Bean
    @ConditionalOnMissingBean(name="smsCodeGenerator")
    public ValidateCodeGenerator smsCodeGenerator(){
        ImageCodeGenerator codGenerator=new ImageCodeGenerator();
        codGenerator.setSecurityProperties(securityProperties);
        return codGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)//当在系统中找到SmsCodeSender的实现的时候，就不会去下面代码到配置里面找信息
    public SmsCodeSender smsCodeSender(){
        return new DefaultSmsCodeSender();
    }

}
