package com.study.service.dto.uc;

import org.springframework.stereotype.Component;
import com.study.service.authentication.JwtUserDetails;

import java.util.HashMap;
import java.util.Map;

/**
 * 存入user token,可以引用缓存系统，存入到缓存。
 */
@Component
public class UserRepository {

    private static final Map<String, JwtUserDetails> userMap = new HashMap<String,JwtUserDetails>();

    public  UserRepository(){
        ReqUserInfoDto user =new ReqUserInfoDto();
        user.setId("1");
        user.setUserName("jojo");
        user.setPassword("123456");
        user.setRole("reporter");
        insert(new JwtUserDetails(user));
    }

    public JwtUserDetails findByUsername(final String username){
        return userMap.get(username);
    }

    public JwtUserDetails insert(JwtUserDetails user){
        userMap.put(user.getUsername(),user);
        return user;
    }

    public void remove(String username){
        userMap.remove(username);
    }
}
