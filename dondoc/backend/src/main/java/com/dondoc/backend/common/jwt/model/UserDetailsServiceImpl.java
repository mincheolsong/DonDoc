package com.dondoc.backend.common.jwt.model;

import com.dondoc.backend.user.entity.User;
import com.dondoc.backend.user.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 유저 정보 확인 로직(핸드폰 번호로 확인)
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // 토큰에서 가져오는 값
        User user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new NoSuchElementException("유저의 정보를 찾을 수 없습니다."));

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("USER"));

        // 유저 정보 구성
        UserDetailsImpl userDetails = new UserDetailsImpl(user.getId().toString(),user.getPhoneNumber(), user.getPassword(), user.getName(),grantedAuthorities);

        return userDetails;
    }
}
