package com.tinqinacademy.bff.domain.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tinqinacademy.authentication.restexport.AuthClient;
import com.tinqinacademy.bff.domain.deserializers.UserDeserializer;
import com.tinqinacademy.comments.restexport.client.CommentsClient;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.support.PageJacksonModule;
import org.springframework.cloud.openfeign.support.SortJacksonModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.tinqinacademy.hotel.restexport.client.HotelClient;
import org.springframework.security.core.userdetails.User;

@Configuration
public class ClientConfiguration {

  @Value("${hotel.client.url}")
  private String HOTEL_CLIENT_URL;
  @Value("${comments.client.url}")
  private String COMMENTS_CLIENT_URL;
  @Value("${auth.client.url}")
  private String AUTH_CLIENT_URL;

  private final ObjectMapper objectMapper;

  public ClientConfiguration() {
    this.objectMapper = new ObjectMapper().registerModules(new JavaTimeModule(), new PageJacksonModule(), new SortJacksonModule())
        .enable(SerializationFeature.INDENT_OUTPUT)
        .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS);
    SimpleModule module = new SimpleModule();
    module.addDeserializer(User.class, new UserDeserializer());
    objectMapper.registerModule(module);
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
