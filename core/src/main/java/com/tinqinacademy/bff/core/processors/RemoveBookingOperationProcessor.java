package com.tinqinacademy.bff.core.processors;

import com.tinqinacademy.bff.api.errors.ErrorResponse;
import com.tinqinacademy.bff.api.operations.removebooking.operation.RemoveBookingOperation;
import com.tinqinacademy.bff.api.operations.removebooking.request.RemoveBookingRequest;
import com.tinqinacademy.bff.api.operations.removebooking.response.RemoveBookingResponse;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import static io.vavr.API.Match;

@Service
public class RemoveBookingOperationProcessor extends BaseOperationProcessor implements RemoveBookingOperation {

  public RemoveBookingOperationProcessor(ConversionService conversionService, Validator validator) {
    super(conversionService, validator);
  }

  @Override
  public Either<? extends ErrorResponse, RemoveBookingResponse> process(RemoveBookingRequest input) {
    return validateInput(input)
        .flatMap(validInput ->
            Try.of(() -> {
                  return RemoveBookingResponse.builder().build();
                })
                .toEither()
                .mapLeft(t -> Match(t).of(
                    defaultCase(t)
                ))
        );
  }
}
