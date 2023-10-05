package com.dondoc.backend.webSocket;
import com.dondoc.backend.common.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;
/*
@Component
@RequiredArgsConstructor
public class WebSocketInterceptor extends HttpSessionHandshakeInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // WebSocket 연결 요청을 처리하기 전에 실행되는 부분입니다.
        // 예: 요청 헤더에서 JWT 토큰을 추출하고 유효성을 검사합니다.
        String token = request.getHeaders().getFirst("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            if (!jwtTokenProvider.isTokenExpired(token)) {
                // JWT 토큰이 유효한 경우, 사용자 정보를 추출하고 속성(attributes)에 저장합니다.
                String userId = jwtTokenProvider.getClaims(token).getSubject();
                attributes.put("userId", userId);
            }
        }

        // WebSocket 연결 허용 여부를 반환합니다. 여기서 true를 반환하면 연결을 허용하고, false를 반환하면 거부합니다.
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        // WebSocket 연결 요청을 처리한 후에 실행되는 부분입니다. 일반적으로 사용되지 않습니다.
    }

}*/