package com.dondoc.backend.common.jwt.model;

import lombok.Getter;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Primary
@Getter
public class UserDetailsImpl implements UserDetails {

    // dondoc에서 사용할 값
    private String userId; // 유저 id
    private String username; // 로그인 아이디
    private String password; // User의 비밀번호
    private String name; // 유저 이름
    private Collection<? extends GrantedAuthority> authorities; // User의 권한 목록

    public UserDetailsImpl(String userId, String username, String password, String name, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    // 유저의 비밀번호 반환 : 해싱처리 된 비밀번호 반환
    @Override
    public String getPassword() {
        return password;
    }

    // 유저의 고유한 값 (핸드폰번호)
    @Override
    public String getUsername() {
        return userId;
    }

    // 계정 만료 여부를 나타내는 로직
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정의 잠금 여부를 나타내는 로직
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 자격 증명 만료 여부 로직
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화 여부 로직
    @Override
    public boolean isEnabled() {
        return true;
    }
}
