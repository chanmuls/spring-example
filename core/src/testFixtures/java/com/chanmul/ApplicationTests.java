package com.chanmul;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class ApplicationTests {
  public void contextLoads() {
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}
