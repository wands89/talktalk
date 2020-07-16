package com.study.service.mapper;

import com.study.service.dto.uc.ReqUserInfoDto;
import com.study.service.entity.UcUserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UcUserInfoMapper {
    Integer addUser(@Param("user") ReqUserInfoDto dto);

    List<UcUserInfo> findUser(@Param("user") ReqUserInfoDto dto);
}
