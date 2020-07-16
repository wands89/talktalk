package com.study.service.properties;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class BrowserProperties {

    private String loginPage = "/signIn.html";
    private LoginType loginType = LoginType.JSON;
    private Integer rememberMeSeconds=3600;

}

