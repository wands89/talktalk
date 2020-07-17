package com.study.service.feign;

import com.study.service.dto.basic.JsonResult;
import com.study.service.dto.uc.ReqUserInfoDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient("uc-server")
public interface UcFeign {
    @PostMapping("/user/getUserInfoInner")
    public JsonResult getUserInfoInner(@RequestParam("userStr") String userStr);

    @GetMapping("/user/me")
    public JsonResult getMe();
}
