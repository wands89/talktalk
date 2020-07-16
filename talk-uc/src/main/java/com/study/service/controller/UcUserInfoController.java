package com.study.service.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.study.service.dto.basic.JsonResult;
import com.study.service.dto.uc.ReqUserInfoDto;
import com.study.service.service.UcUserInfoService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@Slf4j
@EnableSwagger2
@Api("用户中心")
public class UcUserInfoController {

    @Autowired
    UcUserInfoService userInfoService;


    @PostMapping("/register")
    @ApiOperation("注册用户")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "dto", value = "注册用户", required = true, paramType = "body", dataType = "ReqUserInfoDto") })
    public JsonResult register(@RequestBody ReqUserInfoDto dto) {
        String userName = dto.getUserName();
        String nickName = dto.getNickName();
        String password = dto.getPassword();

        boolean validate = StringUtils.isAnyBlank(userName, nickName, password);
        if (validate) {
            throw new RuntimeException("注册信息必填参数不能为空");
        }
        boolean isOk = Boolean.FALSE;
        try {
            isOk = userInfoService.register(dto);
            return JsonResult.success();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return JsonResult.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getLocalizedMessage());
        }
    }
    //    @Autowired
//    private ProviderSignInUtils providerSignInUtils;
//
//    @PostMapping("/regist")
//    public void regist(User user, HttpServletRequest request) {
//
//        //不管是注册用户还是绑定用户，都会拿到一个用户唯一标识。
//        String userId = user.getUsername();
//        providerSignInUtils.doPostSignUp(userId, new ServletWebRequest(request));
//    }

    @GetMapping("/me")
    public Object getCurrentUser(@AuthenticationPrincipal UserDetails user) {
        return user;
    }

    @PostMapping
    @ApiOperation(value = "创建用户")
    public ReqUserInfoDto create(@Valid @RequestBody ReqUserInfoDto user) {

        System.out.println(user.getId());
        System.out.println(user.getUserName());
        System.out.println(user.getPassword());
        System.out.println(user.getBirthday());

        user.setId("1");
        return user;
    }

    @PutMapping("/{id:\\d+}")
    public ReqUserInfoDto update(@Valid @RequestBody ReqUserInfoDto user, BindingResult errors) {

        System.out.println(user.getId());
        System.out.println(user.getUserName());
        System.out.println(user.getPassword());
        System.out.println(user.getBirthday());

        user.setId("1");
        return user;
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(@PathVariable String id) {
        System.out.println(id);
    }

//    @GetMapping
//    @JsonView(User.UserSimpleView.class)
//    @ApiOperation(value = "用户查询服务")
//    public List<User> query(UserQueryCondition condition,
//                            @PageableDefault(page = 2, size = 17, sort = "username,asc") Pageable pageable) {
//
//        System.out.println(ReflectionToStringBuilder.toString(condition, ToStringStyle.MULTI_LINE_STYLE));
//
//        System.out.println(pageable.getPageSize());
//        System.out.println(pageable.getPageNumber());
//        System.out.println(pageable.getSort());
//
//        List<User> users = new ArrayList<>();
//        users.add(new User());
//        users.add(new User());
//        users.add(new User());
//        return users;
//    }

    @GetMapping("/{id:\\d+}")
    @JsonView(ReqUserInfoDto.UserDetailView.class)
    public ReqUserInfoDto getInfo(@ApiParam("用户id") @PathVariable String id) {
        //	throw new UserNotExsitException(id);
        System.out.println("进入getInfo服务");
        ReqUserInfoDto user = new ReqUserInfoDto();
        user.setUserName("tom");
        return user;
    }

}
