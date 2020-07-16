package com.study.service.authentication.code;

import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeProcessor {
    /**
     * 验证码放入Ssession的前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    /**
     * 创建校验码
     *
     * @param request
     * @throws Exception
     */
    void create(ServletWebRequest request) throws Exception;

    /**
     * 校验验证码
     *
     * @param request
     * @throws Exception
     */
    void validate(ServletWebRequest request);
}
