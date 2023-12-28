package com.craig.scholar.happy.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

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
    registry.addEndpoint("/poly-websocket").setAllowedOriginPatterns("*").withSockJS();
    registry.addEndpoint("/poly-websocket").setAllowedOriginPatterns("*");
  }

  @Override
  public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
    registration.setMessageSizeLimit(200000); // default : 64 * 1024
    registration.setSendTimeLimit(20 * 10000); // default : 10 * 10000
    registration.setSendBufferSizeLimit(3 * 512 * 1024);
  }
}
