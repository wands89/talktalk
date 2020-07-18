package com.study.service.browser;

import com.study.service.Utils.JsonUtils;
import com.study.service.authentication.JwtUserDetails;
import com.study.service.dto.basic.JsonResult;
import com.study.service.dto.uc.ReqUserInfoDto;
import com.study.service.feign.UcFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;


@Component
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    private PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    @Autowired
    UcFeign ucFeign;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        JsonResult jsonResult = ucFeign.getUserInfoInner(JsonUtils.toJson(ReqUserInfoDto.builder().userName(name).build()));
        if (!jsonResult.isFlag()) {
            throw new RuntimeException("feign调用失败");
        }
        String pass=null;
        String role=null;
        String resultStr = JsonUtils.toJson(jsonResult.getResult());
        List<ReqUserInfoDto> userInfoDtoList = (List<ReqUserInfoDto>) JsonUtils.transformCollectionsFromJson(resultStr, ReqUserInfoDto.class, List.class);
        if(!CollectionUtils.isEmpty(userInfoDtoList)&&userInfoDtoList.size()==1){
            ReqUserInfoDto userInfoDto=userInfoDtoList.get(0);
            pass=userInfoDto.getPassword();
            role=userInfoDto.getRole();
        }
        ReqUserInfoDto dto = new ReqUserInfoDto();
        dto.setUserName(name);
        dto.setPassword(pass);
        dto.setRole(role);
        return new JwtUserDetails(dto);
    }
}
