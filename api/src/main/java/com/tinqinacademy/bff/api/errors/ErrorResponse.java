package com.tinqinacademy.bff.api.errors;

import com.tinqinacademy.bff.api.base.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatusCode;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ErrorResponse implements Response {

  private List<? extends Error> errors;
  private HttpStatusCode statusCode;
}
