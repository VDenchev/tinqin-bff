package com.tinqinacademy.bff.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.comments.restexport.client.CommentsClient;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ClientConfiguration {

  private static final String COMMENTS_CLIENT_URL = "http://localhost:8081";
  private final ObjectMapper objectMapper;

  @Bean
  public CommentsClient getCommentsClient() {
    return Feign.builder()
        .encoder(new JacksonEncoder(objectMapper))
        .decoder(new JacksonDecoder(objectMapper))
        .target(CommentsClient.class, COMMENTS_CLIENT_URL);
  }
}
