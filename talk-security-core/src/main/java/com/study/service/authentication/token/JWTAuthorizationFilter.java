package com.study.service.authentication.token;

import com.study.service.Utils.JsonUtils;
import com.study.service.authentication.JwtUserDetails;
import com.study.service.dto.basic.JsonResult;
import com.study.service.dto.uc.ReqUserInfoDto;
import com.study.service.dto.uc.UserRepository;
import com.study.service.feign.UcFeign;
import com.study.service.properties.SecurityConstants;
import com.study.service.properties.SecurityProperties;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 验证成功当然就是进行鉴权了
 * 登录成功之后走此类进行鉴权操作
 */
@Component
@Slf4j
public class JWTAuthorizationFilter extends OncePerRequestFilter implements InitializingBean {
    @Resource
    private UserRepository repository;

    @Autowired
    private UcFeign ucFeign;

    /**
     * 系统配置信息
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 存放所有需要校验验证码的url
     */
    private Set<String> urlMap = new HashSet<>();
    /**
     * 验证请求url与配置的url是否匹配的工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 初始化要拦截的url配置信息
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        //根据需要进行放入，暂时不写入
        urlMap.add(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM);
        urlMap.add(securityProperties.getBrowser().getLoginPage());
        urlMap.add(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX);
        urlMap.add(SecurityConstants.DEFAULT_REGISTER_URL);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        try {
            if (needFilter(request)) {
                //先从url中取token
                String authToken = request.getParameter("token");
                String authHeader = request.getHeader(JwtUtils.TOKEN_HEADER);
                if (StringUtils.isNotBlank(authHeader) && authHeader.startsWith(JwtUtils.TOKEN_PREFIX)) {
                    //如果header中存在token，则覆盖掉url中的token
                    authToken = authHeader.substring(JwtUtils.TOKEN_PREFIX.length()); // "Bearer "之后的内容
                }
                if (StringUtils.isNotBlank(authToken)) {
                    String username = JwtUtils.getUsername(authToken);

                    log.info("checking authentication {}", username);
                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        //从已有的user缓存中取了出user信息
                        ReqUserInfoDto reqUserInfoDto=ReqUserInfoDto.builder().userName(username).build();
                        JsonResult jsonResult = ucFeign.getUserInfoInner(JsonUtils.toJson(reqUserInfoDto));
                        if (!jsonResult.isFlag()) {
                            throw new RuntimeException("feign调用失败");
                        }
                        JwtUserDetails user = null;
                        String resultStr = JsonUtils.toJson(jsonResult.getResult());
                        List<ReqUserInfoDto> userInfoDtoList = (List<ReqUserInfoDto>) JsonUtils.transformCollectionsFromJson(resultStr, ReqUserInfoDto.class, List.class);
                        if (CollectionUtils.isEmpty(userInfoDtoList) && userInfoDtoList.size() == 1) {
                            ReqUserInfoDto dto = userInfoDtoList.get(0);
                            user = new JwtUserDetails(dto);
                        }
                        //检查token是否有效
                        if (JwtUtils.validateToken(authToken, user)) {
                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                            //设置用户登录状态
                            log.info("authenticated user {}, setting security context", username);
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    }
                } else {
                    //如果请求中没有token则认为没有权限
                    SecurityContextHolder.getContext().setAuthentication(null);
                }
            }
            chain.doFilter(request, response);
        } catch (ExpiredJwtException exception) {
            throw new RuntimeException("token已过期，请重新登陆！");
        }
    }

    /**
     * 讲系统中配置的需要校验验证码的URL根据校验的类型放入set
     */
    protected void addUrlToSet(String urlString) {
        if (StringUtils.isNotBlank(urlString)) {
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
            urlMap.addAll(Arrays.asList(urls));
        }
    }

    //判断当前的url是否需要token认证，只有登陆页面不需要
    private boolean needFilter(HttpServletRequest request) {
        boolean result = true;
        if (!CollectionUtils.isEmpty(urlMap)) {
            for (String url : urlMap) {
                if (pathMatcher.match(url, request.getRequestURI())) {
                    //一旦匹配上就不需要验证
                    result = false;
                    break;
                }
            }
        }
        return result;

    }
}

