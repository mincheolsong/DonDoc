package com.dondoc.backend.config;

import com.dondoc.backend.common.Filter.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration // 설정
@EnableWebSecurity // Sprint Security 활성화 => URL과 토큰 유무를 통해서 인증
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 인증과 관련된 문제를 다루는데 활용
    // 유저의 정보를 다루는 로직이 있는 클래스
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    // Bcrypt 암호화
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager를 활용하여 사용자 인증을 처리
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    // 자격증명을 검사하는 과정
    // 사용자의 정보를 제공하는 방법과 비밀번호 인코딩 방법을 설정하는 역할
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    // 권한 제어
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // csrf off

        // 로그인 페이지 및 설정
        http.authorizeHttpRequests()
                .antMatchers("/api/user/signin", "/api/user/signup").permitAll()
//                        .anyRequest().authenticated() // 운영 및 테스트용
                .anyRequest().permitAll() // 개발용
                .and()
                .formLogin()
                .loginPage("/login") // 로그인 페이지 경로
                .defaultSuccessUrl("/main")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // SPA 기반므로 세션을 사용하지 않음. => 주로 REST-API에서 활용

        // cors 설정 적용
        http.cors();

        // OncePerRequestFilter 적용
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration(); // cors 설정 생성

        configuration.setAllowCredentials(true); // 내 서버가 응답할 때 json을 JS에서 처리할 수 있게 설정
        configuration.setExposedHeaders(List.of("*")); // 헤더 값 접근

        // 도메인 추가
        configuration.addAllowedOrigin("http://localhost:5174"); // 신뢰하는 도메인 지정
        configuration.addAllowedOrigin("http://localhost:5173"); // 신뢰하는 도메인 지정
        configuration.addAllowedOrigin("http://localhost:9191"); // 신뢰하는 도메인 지정
        configuration.addAllowedOrigin("http://localhost:9090"); // 신뢰하는 도메인 지정
        configuration.addAllowedOrigin("https://j9d108.p.ssafy.io"); // 신뢰하는 도메인 지정
        configuration.addAllowedOrigin("https://j9d108.p.ssafy.io"); // 신뢰하는 도메인 지정
        configuration.addAllowedMethod("*"); // 사용 가능한 메서드 지정
        configuration.addAllowedHeader("*"); // 헤더를 지정하는데 사용 => 토큰 헤더 저장

        // 도메인 이후의 경로에 대한 CORS 정책 설정
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }



}




