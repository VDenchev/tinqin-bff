package com.tinqinacademy.bff.core.processors;

import com.tinqinacademy.bff.api.errors.ErrorResponse;
import com.tinqinacademy.bff.api.operations.updatecomment.operation.UpdateCommentOperation;
import com.tinqinacademy.bff.api.operations.updatecomment.request.UpdateCommentRequest;
import com.tinqinacademy.bff.api.operations.updatecomment.response.UpdateCommentResponse;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import static io.vavr.API.Match;

@Service
public class UpdateCommentOperationProcessor extends BaseOperationProcessor implements UpdateCommentOperation {

  public UpdateCommentOperationProcessor(ConversionService conversionService, Validator validator) {
    super(conversionService, validator);
  }

  @Override
  public Either<? extends ErrorResponse, UpdateCommentResponse> process(UpdateCommentRequest input) {
    return validateInput(input)
        .flatMap(validInput ->
            Try.of(() -> {
                  return UpdateCommentResponse.builder().build();
                })
                .toEither()
                .mapLeft(t -> Match(t).of(
                    defaultCase(t)
                ))
        );
  }
}
