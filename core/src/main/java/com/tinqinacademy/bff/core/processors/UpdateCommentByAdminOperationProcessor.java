package com.tinqinacademy.bff.core.processors;

import com.tinqinacademy.bff.api.errors.ErrorResponse;
import com.tinqinacademy.bff.api.operations.updatecommentbyadmin.operation.UpdateCommentByAdminOperation;
import com.tinqinacademy.bff.api.operations.updatecommentbyadmin.request.UpdateCommentByAdminRequest;
import com.tinqinacademy.bff.api.operations.updatecommentbyadmin.response.UpdateCommentByAdminResponse;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import static io.vavr.API.Match;

@Service
public class UpdateCommentByAdminOperationProcessor extends BaseOperationProcessor implements UpdateCommentByAdminOperation {

  public UpdateCommentByAdminOperationProcessor(ConversionService conversionService, Validator validator) {
    super(conversionService, validator);
  }

  @Override
  public Either<? extends ErrorResponse, UpdateCommentByAdminResponse> process(UpdateCommentByAdminRequest input) {
    return validateInput(input)
        .flatMap(validInput ->
            Try.of(() -> {
                  return UpdateCommentByAdminResponse.builder().build();
                })
                .toEither()
                .mapLeft(t -> Match(t).of(
                    defaultCase(t)
                ))
        );
  }
}
