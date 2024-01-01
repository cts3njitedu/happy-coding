package com.craig.scholar.happy.service.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Slf4j
public class WebSocketEventListener {

  @EventListener
  private void handleSessionConnected(SessionConnectEvent event) {
    log.info("Connecting to websocket for session", event.getMessage().getHeaders());
  }

  @EventListener
  private void handleSessionDisconnect(SessionDisconnectEvent event) {
    log.info("Disconnecting from websocket for session");
  }
}
