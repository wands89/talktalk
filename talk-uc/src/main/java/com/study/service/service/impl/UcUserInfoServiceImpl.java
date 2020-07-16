package com.study.service.service.impl;

import com.study.service.dto.uc.ReqUserInfoDto;
import com.study.service.entity.UcUserInfo;
import com.study.service.mapper.UcUserInfoMapper;
import com.study.service.service.UcUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UcUserInfoServiceImpl implements UcUserInfoService {
    @Resource
    UcUserInfoMapper userInfoMapper;
@Autowired
PasswordEncoder passwordEncoder;
    @Override
    public boolean register(ReqUserInfoDto dto) throws Exception {
        //查重
        List<UcUserInfo> users = userInfoMapper.findUser(dto);
        if (!CollectionUtils.isEmpty(users)) {
            throw new RuntimeException("当前用户名已存在，请重命名！");
        }
        String  authenPass=passwordEncoder.encode("123456");
        log.error("注册秘密："+authenPass);
        dto.setPassword(authenPass);
        Integer result = userInfoMapper.addUser(dto);
        if (result > 0) {
            return true;
        }
        return false;
    }
}
