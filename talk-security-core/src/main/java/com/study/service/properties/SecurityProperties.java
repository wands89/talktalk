package com.study.service.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "talk.security")
@Configuration
@Data
public class SecurityProperties {
   private BrowserProperties browser=new BrowserProperties();
   private ValidateCodeProperties code=new ValidateCodeProperties();
}
