package com.tinqinacademy.bff.domain.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.tinqinacademy.authentication.restexport.AuthClient;
import com.tinqinacademy.bff.domain.deserializers.UserDeserializer;
import com.tinqinacademy.comments.restexport.client.CommentsClient;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.tinqinacademy.hotel.restexport.client.HotelClient;
import org.springframework.security.core.userdetails.User;

@Configuration
public class ClientConfiguration {

  private static final String COMMENTS_CLIENT_URL = "http://localhost:8081";
  private static final String HOTEL_CLIENT_URL = "http://localhost:8080";
  private static final String AUTH_CLIENT_URL = "http://localhost:8082";

  private final ObjectMapper objectMapper;

  @Autowired
  public ClientConfiguration(ObjectMapper objectMapper) {
    SimpleModule module = new SimpleModule();
    module.addDeserializer(User.class,new UserDeserializer());
    objectMapper.registerModule(module);
    this.objectMapper = objectMapper;
  }

  @Bean
  public CommentsClient commentsClient() {
    return Feign.builder()
        .client(new OkHttpClient())
        .encoder(new JacksonEncoder(objectMapper))
        .decoder(new JacksonDecoder(objectMapper))
        .target(CommentsClient.class, COMMENTS_CLIENT_URL);
  }

  @Bean
  public HotelClient hotelClient() {
    return Feign.builder()
        .client(new OkHttpClient())
        .encoder(new JacksonEncoder(objectMapper))
        .decoder(new JacksonDecoder(objectMapper))
        .target(HotelClient.class, HOTEL_CLIENT_URL);
  }
  @Bean
  public AuthClient authClient() {
    return Feign.builder()
        .client(new OkHttpClient())
        .encoder(new JacksonEncoder(objectMapper))
        .decoder(new JacksonDecoder(objectMapper))
        .target(AuthClient.class, AUTH_CLIENT_URL);
  }
}
