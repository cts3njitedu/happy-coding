package com.craig.scholar.happy.handler;

import com.craig.scholar.happy.model.StompPrincipal;
import java.security.Principal;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

public class CustomWebSocketHandler extends DefaultHandshakeHandler {
  @Override
  protected Principal determineUser(
      ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes
  ) {
    // Generate principal with UUID as name
    return new StompPrincipal(UUID.randomUUID().toString());
  }
}
