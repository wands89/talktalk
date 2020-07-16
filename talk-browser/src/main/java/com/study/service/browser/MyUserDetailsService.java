package com.study.service.browser;

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


@Component
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    private PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

@Autowired
UcFeign ucFeign;
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
//        List<UcUserInfo> users = userInfoMapper.findUser(ReqUserInfoDto.builder().userName(name).build());
//        UcUserInfo user = new UcUserInfo();
//        if (!CollectionUtils.isEmpty(users) && users.size() == 1) {
//            user = users.get(0);
//
//        }
//        String pass =user.getPassword();
        JsonResult jsonResult=ucFeign.getTest();
        System.out.println(jsonResult.getResult().toString());
        String pass =passwordEncoder.encode("123456");
        log.error("秘密是：" + pass);
//        return new User(name, pass,
//                true, true, true, true,
//                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        ReqUserInfoDto dto = new ReqUserInfoDto();
        dto.setUserName(name);
        dto.setPassword(pass);
        dto.setRole("admin");
        return new JwtUserDetails(dto);
    }
}
