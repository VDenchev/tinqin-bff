package com.tinqinacademy.bff.core.processors;

import com.tinqinacademy.bff.api.errors.ErrorResponse;
import com.tinqinacademy.bff.api.operations.getroom.operation.GetRoomOperation;
import com.tinqinacademy.bff.api.operations.getroom.request.GetRoomRequest;
import com.tinqinacademy.bff.api.operations.getroom.response.GetRoomResponse;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import static io.vavr.API.Match;

@Service
public class GetRoomOperationProcessor extends BaseOperationProcessor implements GetRoomOperation {

  public GetRoomOperationProcessor(ConversionService conversionService, Validator validator) {
    super(conversionService, validator);
  }

  @Override
  public Either<? extends ErrorResponse, GetRoomResponse> process(GetRoomRequest input) {
    return validateInput(input)
        .flatMap(validInput ->
            Try.of(() -> {
                  return GetRoomResponse.builder().build();
                })
                .toEither()
                .mapLeft(t -> Match(t).of(
                    defaultCase(t)
                ))
        );
  }
}
