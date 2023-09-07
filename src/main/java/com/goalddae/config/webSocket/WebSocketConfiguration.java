package com.goalddae.config.webSocket;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Component
public class WebSocketConfiguration {
    // @ServerEndPoint 로 정의된 클래스는 WebSocket 생성시 인스턴스를 생성라여 JWA에 의해 관리되기 때문에 @Autowired가 설정된 멤버변수들이 정상 초기와 되지 않는다
    // 그래서 해당 클래스를 통해 이를 해결
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
