package com.study.service.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.service.properties.LoginType;
import com.study.service.properties.SecurityProperties;
import com.study.service.authentication.token.JwtUtils;
import com.study.service.authentication.JwtUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Slf4j
@Component
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SecurityProperties securityProperties;

    // 成功验证后调用的方法
    // 如果验证成功，就生成token并返回
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authResult) throws IOException, ServletException {

        log.info(" 登陆成功！！");
        if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            JwtUserDetails jwtUserDetails = (JwtUserDetails) authResult.getPrincipal();
            System.out.println("jwtUser:" + jwtUserDetails.toString());

            String role = "";
            Collection<? extends GrantedAuthority> authorities = jwtUserDetails.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                role = authority.getAuthority();
            }

            String token = JwtUtils.createToken(jwtUserDetails.getUsername(), role);
            //String token = JwtTokenUtils.createToken(jwtUser.getUsername(), false);
            // 返回创建成功的token
            // 但是这里创建的token只是单纯的token
            // 按照jwt的规定，最后请求的时候应该是 `Bearer token`
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json; charset=utf-8");
            String tokenStr = JwtUtils.TOKEN_PREFIX + token;
            httpServletResponse.setHeader("token", tokenStr);
            log.error("token: "+token);
        }

        else{
            super.onAuthenticationSuccess(httpServletRequest, httpServletResponse, authResult);
        }
    }


}
