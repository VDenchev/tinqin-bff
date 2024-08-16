package com.tinqinacademy.bff.core.processors;

import com.tinqinacademy.bff.api.errors.Error;
import com.tinqinacademy.bff.api.errors.ErrorResponse;
import com.tinqinacademy.bff.api.base.OperationRequest;
import io.vavr.API;
import io.vavr.control.Either;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.List;
import java.util.Set;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;

@RequiredArgsConstructor
public abstract class BaseOperationProcessor {

  protected final ConversionService conversionService;
  protected final Validator validator;

  protected API.Match.Case<? extends Throwable, ErrorResponse> customStatusCase(Throwable t, Class<? extends Throwable> clazz, HttpStatusCode statusCode) {
    ErrorResponse output = ErrorResponse.builder()
        .statusCode(statusCode)
        .errors(List.of(Error.builder()
            .message(t.getMessage())
            .build()
        ))
        .build();
    return createCase(clazz, output);
  }

  protected API.Match.Case<? extends Throwable, ErrorResponse> defaultCase(Throwable t) {
    ErrorResponse output = ErrorResponse.builder()
        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
        .errors(List.of(Error.builder()
            .message(t.getMessage())
            .build()
        ))
        .build();
    return createDefaultCase(output);
  }
  private API.Match.Case<? extends Throwable, ErrorResponse> createCase(Class<? extends Throwable> clazz, ErrorResponse ErrorResponse) {
    return Case($(instanceOf(clazz)), ErrorResponse);
  }

  private API.Match.Case<? extends Throwable, ErrorResponse> createDefaultCase(ErrorResponse ErrorResponse) {
    return Case($(), ErrorResponse);
  }


  protected <T extends OperationRequest> Either<ErrorResponse, T> validateInput(T input) {
    return validateInput(input, Default.class);
  }

  protected <T extends OperationRequest> Either<ErrorResponse, T> validateInput(T input, Class<?> validationGroupClass) {
    Set<ConstraintViolation<T>> violations = validator.validate(input, validationGroupClass);
    if (violations.isEmpty()) {
      return Either.right(input);
    }

    List<Error> errors = violations.stream()
        .map(v -> Error.builder()
            .message(v.getMessage())
            .field(v.getPropertyPath().toString())
            .build()
        ).toList();

    return Either.left(
        ErrorResponse.builder()
            .errors(errors)
            .statusCode(HttpStatus.UNPROCESSABLE_ENTITY)
            .build());
  }
}