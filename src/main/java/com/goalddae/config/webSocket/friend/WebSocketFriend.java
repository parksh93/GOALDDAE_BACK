package com.goalddae.config.webSocket.friend;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
@ServerEndpoint("/socket/friend")
public class WebSocketFriend {
    private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen // ServerEndPoint 에 요청이 들어왔을 때 실행되는 메서드, 클라이언트의 정보를 매개변수로 전달
    public void onOpen(Session session) throws Exception {
        System.out.println("open session : " + session.toString());

        if(!clients.contains(session)) {
            clients.add(session);
        }
    }

    @OnMessage  // 클라이언트와 server가 연결되었을때 메세지가 전달되면 해당 메서드가 실행되어 clients에 있는 모든 사용자에게 session 메세지 전달
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("setMessage" + message);

        for (Session s : clients) {
            s.getBasicRemote().sendText(message);
        }
    }

    @OnClose    // 클라이언트가 URL을 바꾸거나 브라우저를 종료하면 실행, 클라이언트 세션정보를 clients에서 제거
    public void onClose(Session session) {
        System.out.println("close session : " + session.toString());
        clients.remove(session);
    }
}
