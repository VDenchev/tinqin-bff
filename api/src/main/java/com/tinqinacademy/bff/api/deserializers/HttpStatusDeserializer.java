package com.tinqinacademy.bff.api.deserializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.io.IOException;

public class HttpStatusDeserializer extends JsonDeserializer<HttpStatusCode> {
  @Override
  public HttpStatusCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
    String statusAsString = p.getText();
    return HttpStatus.valueOf(statusAsString);
  }
}
