package com.tinqinacademy.bff.core.processors;

import com.tinqinacademy.bff.api.errors.ErrorResponse;
import com.tinqinacademy.bff.api.operations.bookroom.operation.BookRoomOperation;
import com.tinqinacademy.bff.api.operations.bookroom.request.BookRoomRequest;
import com.tinqinacademy.bff.api.operations.bookroom.response.BookRoomResponse;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import static io.vavr.API.Match;

@Service
public class BookRoomOperationProcessor extends BaseOperationProcessor implements BookRoomOperation {

  public BookRoomOperationProcessor(ConversionService conversionService, Validator validator) {
    super(conversionService, validator);
  }

  @Override
  public Either<? extends ErrorResponse, BookRoomResponse> process(BookRoomRequest input) {
    return validateInput(input)
        .flatMap(validInput ->
            Try.of(() -> {
                  return BookRoomResponse.builder().build();
                })
                .toEither()
                .mapLeft(t -> Match(t).of(
                    defaultCase(t)
                ))
        );
  }
}
