package com.tinqinacademy.bff.core.processors;

import com.tinqinacademy.bff.api.errors.ErrorResponse;
import com.tinqinacademy.bff.api.operations.deletecomment.operation.DeleteCommentOperation;
import com.tinqinacademy.bff.api.operations.deletecomment.request.DeleteCommentRequest;
import com.tinqinacademy.bff.api.operations.deletecomment.response.DeleteCommentResponse;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import static io.vavr.API.Match;

@Service
public class DeleteCommentOperationProcessor extends BaseOperationProcessor implements DeleteCommentOperation {

  public DeleteCommentOperationProcessor(ConversionService conversionService, Validator validator) {
    super(conversionService, validator);
  }

  @Override
  public Either<? extends ErrorResponse, DeleteCommentResponse> process(DeleteCommentRequest input) {
    return validateInput(input)
        .flatMap(validInput ->
            Try.of(() -> {
                  return DeleteCommentResponse.builder().build();
                })
                .toEither()
                .mapLeft(t -> Match(t).of(
                    defaultCase(t)
                ))
        );
  }
}
