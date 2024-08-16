package com.tinqinacademy.bff.core.processors;

import com.tinqinacademy.bff.api.errors.ErrorResponse;
import com.tinqinacademy.bff.api.operations.updateroom.operation.UpdateRoomOperation;
import com.tinqinacademy.bff.api.operations.updateroom.request.UpdateRoomRequest;
import com.tinqinacademy.bff.api.operations.updateroom.response.UpdateRoomResponse;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import static io.vavr.API.Match;

@Service
public class UpdateRoomOperationProcessor extends BaseOperationProcessor implements UpdateRoomOperation {

  public UpdateRoomOperationProcessor(ConversionService conversionService, Validator validator) {
    super(conversionService, validator);
  }

  @Override
  public Either<? extends ErrorResponse, UpdateRoomResponse> process(UpdateRoomRequest input) {
    return validateInput(input)
        .flatMap(validInput ->
            Try.of(() -> {
                  return UpdateRoomResponse.builder().build();
                })
                .toEither()
                .mapLeft(t -> Match(t).of(
                    defaultCase(t)
                ))
        );
  }
}
