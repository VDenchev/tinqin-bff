package com.tinqinacademy.bff.core.processors;

import com.tinqinacademy.bff.api.errors.ErrorResponse;
import com.tinqinacademy.bff.api.operations.addroom.operation.AddRoomOperation;
import com.tinqinacademy.bff.api.operations.addroom.request.AddRoomRequest;
import com.tinqinacademy.bff.api.operations.addroom.response.AddRoomResponse;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import static io.vavr.API.Match;

@Service
public class AddRoomOperationProcessor extends BaseOperationProcessor implements AddRoomOperation {

  public AddRoomOperationProcessor(ConversionService conversionService, Validator validator) {
    super(conversionService, validator);
  }

  @Override
  public Either<? extends ErrorResponse, AddRoomResponse> process(AddRoomRequest input) {
    return validateInput(input)
        .flatMap(validInput ->
            Try.of(() -> {
                  return AddRoomResponse.builder().build();
                })
                .toEither()
                .mapLeft(t -> Match(t).of(
                    defaultCase(t)
                ))
        );
  }
}
