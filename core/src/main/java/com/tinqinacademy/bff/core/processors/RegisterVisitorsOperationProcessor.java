package com.tinqinacademy.bff.core.processors;

import com.tinqinacademy.bff.api.errors.ErrorResponse;
import com.tinqinacademy.bff.api.operations.registervisitors.operation.RegisterVisitorsOperation;
import com.tinqinacademy.bff.api.operations.registervisitors.request.RegisterVisitorsRequest;
import com.tinqinacademy.bff.api.operations.registervisitors.response.RegisterVisitorsResponse;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import static io.vavr.API.Match;

@Service
public class RegisterVisitorsOperationProcessor extends BaseOperationProcessor implements RegisterVisitorsOperation {

  public RegisterVisitorsOperationProcessor(ConversionService conversionService, Validator validator) {
    super(conversionService, validator);
  }

  @Override
  public Either<? extends ErrorResponse, RegisterVisitorsResponse> process(RegisterVisitorsRequest input) {
    return validateInput(input)
        .flatMap(validInput ->
            Try.of(() -> {
                  return RegisterVisitorsResponse.builder().build();
                })
                .toEither()
                .mapLeft(t -> Match(t).of(
                    defaultCase(t)
                ))
        );
  }
}
