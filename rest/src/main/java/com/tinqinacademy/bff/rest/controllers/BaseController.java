package com.tinqinacademy.bff.rest.controllers;

import com.tinqinacademy.bff.api.base.OperationResponse;
import com.tinqinacademy.bff.api.base.Response;
import com.tinqinacademy.bff.api.errors.ErrorResponse;
import com.tinqinacademy.bff.api.models.response.CustomUser;
import io.vavr.control.Either;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

public class BaseController {

  protected ResponseEntity<Response> createResponse(
      Either<? extends ErrorResponse, ? extends OperationResponse> either,
      HttpStatusCode statusCode
  ) {
    return either
        .fold(
            error -> new ResponseEntity<>(error, error.getStatusCode()),
            output -> new ResponseEntity<>(output, statusCode)
        );
  }

  protected CustomUser getUserFromContext() {
    CustomUser principal = (CustomUser) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

    return principal;
  }
}
