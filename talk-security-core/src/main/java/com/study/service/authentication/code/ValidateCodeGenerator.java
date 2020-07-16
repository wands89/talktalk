package com.study.service.authentication.code;

import org.springframework.web.context.request.ServletWebRequest;

public interface  ValidateCodeGenerator {
    ValidateCode generate(ServletWebRequest request);
}
