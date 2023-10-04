package com.dondoc.backend.common.Filter;

import com.dondoc.backend.common.exception.JwtAuthFilterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class LogoutFilter extends OncePerRequestFilter {

    private final RedisTemplate<String, Object> redisTemplate;

    private final JwtAuthFilterException jwtAuthFilterException;

    public LogoutFilter(RedisTemplate<String, Object> redisTemplate, JwtAuthFilterException jwtAuthFilterException) {
        this.redisTemplate = redisTemplate;
        this.jwtAuthFilterException = jwtAuthFilterException;
    }

    private static final List<String> EXCLUDE_URL = List.of(
//            "/api/user/find_user"
            "/api/user/logout"
            ,"/api/user/signup"
            ,"/api/user/signin"
            ,"/api/user/find_password"
            ,"/api/user/sms/signup"
            ,"/api/user/sms/find_password"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = ((HttpServletRequest) request).getServletPath();

        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/swagger") || requestURI.startsWith("/v2/api-docs") ||
                requestURI.startsWith("/swagger-resources/") || requestURI.startsWith("/webjars/") ||
                requestURI.startsWith("/websocket")) {
            filterChain.doFilter(request, response);
            return;
        }

        if(!EXCLUDE_URL.contains(path)) {
            String token = request.getHeader("Authorization").substring(7);

            try {
                // 블랙리스트 존재 확인
                if (redisTemplate.opsForSet().isMember("black", token)) {
                    // 인증정보가 존재하지 않음
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setContentType("application/json");
                    response.getWriter().write(jwtAuthFilterException.black());

                } else {
                    filterChain.doFilter(request, response);
                }
            } catch (NullPointerException e) {
                // 토큰이 존재하지 않음
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("application/json");
                response.getWriter().write(jwtAuthFilterException.noAuthentication());
            }
        }else{
            filterChain.doFilter(request,response);
        }

    }
}
