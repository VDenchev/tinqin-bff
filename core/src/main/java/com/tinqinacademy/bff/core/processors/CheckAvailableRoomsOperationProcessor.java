package com.tinqinacademy.bff.core.processors;

import com.tinqinacademy.bff.api.errors.ErrorResponse;
import com.tinqinacademy.bff.api.operations.checkavailablerooms.operation.CheckAvailableRoomsOperation;
import com.tinqinacademy.bff.api.operations.checkavailablerooms.request.CheckAvailableRoomsRequest;
import com.tinqinacademy.bff.api.operations.checkavailablerooms.response.CheckAvailableRoomsResponse;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import static io.vavr.API.Match;

@Service
public class CheckAvailableRoomsOperationProcessor extends BaseOperationProcessor implements CheckAvailableRoomsOperation {

  public CheckAvailableRoomsOperationProcessor(ConversionService conversionService, Validator validator) {
    super(conversionService, validator);
  }

  @Override
  public Either<? extends ErrorResponse, CheckAvailableRoomsResponse> process(CheckAvailableRoomsRequest input) {
    return validateInput(input)
        .flatMap(validInput ->
            Try.of(() -> {
                  return CheckAvailableRoomsResponse.builder().build();
                })
                .toEither()
                .mapLeft(t -> Match(t).of(
                    defaultCase(t)
                ))
        );
  }
}
