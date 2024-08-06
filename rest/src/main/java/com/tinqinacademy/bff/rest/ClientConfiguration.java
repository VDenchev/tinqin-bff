package com.tinqinacademy.bff.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.comments.restexport.client.CommentsClient;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.tinqinacademy.hotel.restexport.client.HotelClient;

@Configuration
@RequiredArgsConstructor
public class ClientConfiguration {

  private static final String COMMENTS_CLIENT_URL = "http://localhost:8081";
  private static final String HOTEL_CLIENT_URL = "http://localhost:8080";
  private final ObjectMapper objectMapper;

  @Bean
  public CommentsClient commentsClient() {
    return Feign.builder()
        .encoder(new JacksonEncoder(objectMapper))
        .decoder(new JacksonDecoder(objectMapper))
        .target(CommentsClient.class, COMMENTS_CLIENT_URL);
  }

  @Bean
  public HotelClient hotelClient() {
    return Feign.builder()
        .encoder(new JacksonEncoder(objectMapper))
        .decoder(new JacksonDecoder(objectMapper))
        .target(HotelClient.class, HOTEL_CLIENT_URL);
  }
}
