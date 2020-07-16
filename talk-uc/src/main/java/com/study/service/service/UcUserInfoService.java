package com.study.service.service;

import com.study.service.dto.uc.ReqUserInfoDto;

public interface UcUserInfoService {
    boolean register(ReqUserInfoDto dto) throws Exception;
}
