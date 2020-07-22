package com.study.service.authentication;


import com.study.service.dto.uc.ReqUserInfoDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

    public class JwtUserDetails implements UserDetails {

        private Integer id;
        private String username;
        private String password;
        private Collection<? extends GrantedAuthority> authorities;

        public JwtUserDetails() {
        }

        // 写一个能直接使用user创建jwtUser的构造器
        public JwtUserDetails(ReqUserInfoDto user) {
            username = user.getUserName();
            password = user.getPassword();
            user.setRole("admin");
            authorities = Collections.singleton(new SimpleGrantedAuthority(user.getRole()));
        }

        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = authorities;
        }

    }
