//package com.dondoc.backend.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//import java.util.Collections;
//import java.util.List;
//
//@Configuration
//public class WebSocketCorsConfig {
//
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//
//        // WebSocket 연결을 위한 경로에 대한 CORS 설정
//        config.addAllowedOriginPattern("*"); // 모든 오리진 허용 (실제 운영 환경에서는 제한 필요)
//        config.addAllowedMethod("*"); // 모든 HTTP 메서드 허용
//        config.addAllowedHeader("*"); // 모든 헤더 허용
//        config.setAllowCredentials(true); // 인증 정보 전송을 허용
//
//        source.registerCorsConfiguration("/websocket", config); // WebSocket 경로에 대한 CORS 설정
//
//        return new CorsFilter(source);
//    }
//}
