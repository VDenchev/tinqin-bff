package com.tinqinacademy.bff.core.processors;

import com.tinqinacademy.bff.api.errors.ErrorResponse;
import com.tinqinacademy.bff.api.operations.addcomment.operation.AddCommentOperation;
import com.tinqinacademy.bff.api.operations.addcomment.request.AddCommentRequest;
import com.tinqinacademy.bff.api.operations.addcomment.response.AddCommentResponse;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import static io.vavr.API.Match;

@Service
public class AddCommentOperationProcessor extends BaseOperationProcessor implements AddCommentOperation {

  public AddCommentOperationProcessor(ConversionService conversionService, Validator validator) {
    super(conversionService, validator);
  }

  @Override
  public Either<? extends ErrorResponse, AddCommentResponse> process(AddCommentRequest input) {
    return validateInput(input)
        .flatMap(validInput ->
            Try.of(() -> {
                  return AddCommentResponse.builder().build();
                })
                .toEither()
                .mapLeft(t -> Match(t).of(
                    defaultCase(t)
                ))
        );
  }

}
