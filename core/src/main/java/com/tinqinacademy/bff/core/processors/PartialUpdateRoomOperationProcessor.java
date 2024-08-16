package com.tinqinacademy.bff.core.processors;

import com.tinqinacademy.bff.api.errors.ErrorResponse;
import com.tinqinacademy.bff.api.operations.partialupdateroom.operation.PartialUpdateRoomOperation;
import com.tinqinacademy.bff.api.operations.partialupdateroom.request.PartialUpdateRoomRequest;
import com.tinqinacademy.bff.api.operations.partialupdateroom.response.PartialUpdateRoomResponse;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import static io.vavr.API.Match;

@Service
public class PartialUpdateRoomOperationProcessor extends BaseOperationProcessor implements PartialUpdateRoomOperation {

  public PartialUpdateRoomOperationProcessor(ConversionService conversionService, Validator validator) {
    super(conversionService, validator);
  }

  @Override
  public Either<? extends ErrorResponse, PartialUpdateRoomResponse> process(PartialUpdateRoomRequest input) {
    return validateInput(input)
        .flatMap(validInput ->
            Try.of(() -> {
                  return PartialUpdateRoomResponse.builder().build();
                })
                .toEither()
                .mapLeft(t -> Match(t).of(
                    defaultCase(t)
                ))
        );
  }
}
