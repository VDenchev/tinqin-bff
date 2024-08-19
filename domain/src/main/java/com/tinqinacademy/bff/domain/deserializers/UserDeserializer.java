package com.tinqinacademy.bff.domain.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserDeserializer extends JsonDeserializer<User> {

  @Override
  public User deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);

    String username = node.get("username").asText();
    String password = node.get("password").asText();
    boolean enabled = node.get("enabled").asBoolean();
    boolean accountNonExpired = node.get("accountNonExpired").asBoolean();
    boolean accountNonLocked = node.get("accountNonLocked").asBoolean();
    boolean credentialsNonExpired = node.get("credentialsNonExpired").asBoolean();

    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    for (JsonNode authorityNode : node.get("authorities")) {
      authorities.add(new SimpleGrantedAuthority(authorityNode.get("authority").asText()));
    }

    return new User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
  }
}
