package com.craig.scholar.happy.config;

import jakarta.servlet.http.HttpSession;
import java.util.Map;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@EnableWebSocketMessageBroker
@ComponentScan(basePackages = "com.craig.scholar.happy")
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/queue");
    config.setApplicationDestinationPrefixes("/app");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/poly-websocket")
        .setAllowedOriginPatterns("*")
        .setHandshakeHandler(new DefaultHandshakeHandler() {
          public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
              WebSocketHandler wsHandler, Map attributes) throws Exception {
            if (request instanceof ServletServerHttpRequest) {
              ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) request;
              HttpSession session = servletServerHttpRequest.getServletRequest().getSession();
              attributes.put("sessionId", session.getId());
            }
            return true;
          }
        })
        .withSockJS();
    registry.addEndpoint("/poly-websocket")
        .setHandshakeHandler(new DefaultHandshakeHandler() {
          public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
              WebSocketHandler wsHandler, Map attributes) throws Exception {
            if (request instanceof ServletServerHttpRequest) {
              ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) request;
              HttpSession session = servletServerHttpRequest.getServletRequest().getSession();
              attributes.put("sessionId", session.getId());
            }
            return true;
          }
        })
        .setAllowedOriginPatterns("*");
//        .setHandshakeHandler(new CustomWebSocketHandler());
  }

  @Override
  public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
    registration.setMessageSizeLimit(200000); // default : 64 * 1024
    registration.setSendTimeLimit(20 * 10000); // default : 10 * 10000
//    registration.sets
    registration.setSendBufferSizeLimit(Integer.MAX_VALUE);
  }
}
