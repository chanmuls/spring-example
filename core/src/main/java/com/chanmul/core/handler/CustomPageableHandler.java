package com.chanmul.core.handler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

@Configuration
public class CustomPageableHandler {
  @Bean
  public PageableHandlerMethodArgumentResolverCustomizer customize() {
    return p -> p.setOneIndexedParameters(true);
  }
}
