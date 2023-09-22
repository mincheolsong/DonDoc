package com.dondoc.backend.webSocket;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
public class WebSocketInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    @EventListener
    public void handleSessionConnected(SessionConnectedEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String token = accessor.getFirstNativeHeader("Authorization"); // Extract JWT token
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            // Validate and decode JWT token, and extract user information
            if(jwtTokenProvider.isTokenValid(token)){
                String userId = jwtTokenProvider.getUserIdFromToken(token);
                accessor.getSessionAttributes().put("userId", userId);
            }
            // Store user information in WebSocketSession, e.g., using accessor.getSessionAttributes()
        }
    }

    // You can also handle disconnect events to clean up session-related data if needed.
    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String userId = (String) accessor.getSessionAttributes().get("userId");

        if (userId != null) {
            // 연결이 끊어진 사용자의 userId 정보를 제거
            accessor.getSessionAttributes().remove("userId");

            // 로그아웃 시간 기록 또는 로그아웃 이벤트 처리
            // 로그아웃 시간을 데이터베이스에 기록하는 등의 작업 수행 가능
            // 예를 들어, 로그아웃 시간을 기록하려면 다음과 같이 할 수 있습니다:
            // logoutService.recordLogoutTime(userId);
        }

    }
}