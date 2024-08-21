package com.tinqinacademy.bff.domain.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.tinqinacademy.authentication.api.models.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class CustomUserDetailsDeserializer extends JsonDeserializer<CustomUserDetails> {

  @Override
  public CustomUserDetails deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    log.info("node: {}", node);

    String userId = node.get("userId").asText();
    String username = node.get("username").asText();
    String password = node.get("password").asText();
    boolean enabled = node.get("enabled").asBoolean();
    boolean accountNonExpired = node.get("accountNonExpired").asBoolean();
    boolean accountNonLocked = node.get("accountNonLocked").asBoolean();
    boolean credentialsNonExpired = node.get("credentialsNonExpired").asBoolean();

    List<GrantedAuthority> authorities = new ArrayList<>();
    for (JsonNode authorityNode : node.get("authorities")) {
      authorities.add(new SimpleGrantedAuthority(authorityNode.get("authority").asText()));
    }

    return CustomUserDetails.builder()
        .userId(UUID.fromString(userId))
        .username(username)
        .password(password)
        .enabled(enabled)
        .accountNonExpired(accountNonExpired)
        .accountNonLocked(accountNonLocked)
        .credentialsNonExpired(credentialsNonExpired)
        .authorities(authorities)
        .build();
  }
}
