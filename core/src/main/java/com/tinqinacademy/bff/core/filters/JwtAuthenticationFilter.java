package com.tinqinacademy.bff.core.filters;

import com.tinqinacademy.authentication.api.models.CustomUserDetails;
import com.tinqinacademy.authentication.restexport.AuthClient;
import com.tinqinacademy.bff.api.models.response.CustomUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final AuthClient authClient;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    String authHeaderValue = request.getHeader("Authorization");
    String bearerToken = "";
    if (authHeaderValue != null && authHeaderValue.startsWith("Bearer ")) {
      bearerToken = authHeaderValue.substring(7);
    }
    try {

      CustomUserDetails userDetails = authClient.validateToken(bearerToken).getUser();

      CustomUser user = CustomUser.builder()
          .userId(userDetails.getUserId())
          .username(userDetails.getUsername())
          .password(userDetails.getPassword())
          .enabled(userDetails.isEnabled())
          .accountNonExpired(userDetails.isAccountNonExpired())
          .accountNonLocked(userDetails.isAccountNonLocked())
          .credentialsNonExpired(userDetails.isCredentialsNonExpired())
          .authorities(userDetails.getAuthorities())
          .build();

      UsernamePasswordAuthenticationToken authenticationToken =
          new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

      authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    } catch (Exception e) {
      logger.info("Filter authentication failed");
      e.printStackTrace();
    } finally {
      filterChain.doFilter(request, response);
    }
  }
}
