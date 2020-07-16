package com.study.service.authentication.code.sms;

import com.study.service.authentication.code.ValidateCode;
import com.study.service.authentication.code.ValidateCodeGenerator;
import com.study.service.properties.SecurityProperties;
import lombok.Data;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

@Data
@Component("smsValidateCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    SecurityProperties securityProperties;

    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String code= RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());//长度
        return new ValidateCode(code, securityProperties.getCode().getSms().getExpiredIn());
    }

}
