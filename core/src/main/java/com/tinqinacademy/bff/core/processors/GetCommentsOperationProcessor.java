package com.tinqinacademy.bff.core.processors;

import com.tinqinacademy.bff.api.errors.ErrorResponse;
import com.tinqinacademy.bff.api.operations.getcomments.operation.GetCommentsOperation;
import com.tinqinacademy.bff.api.operations.getcomments.request.GetCommentsRequest;
import com.tinqinacademy.bff.api.operations.getcomments.response.GetCommentsResponse;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import static io.vavr.API.Match;

@Service
public class GetCommentsOperationProcessor extends BaseOperationProcessor implements GetCommentsOperation {

  public GetCommentsOperationProcessor(ConversionService conversionService, Validator validator) {
    super(conversionService, validator);
  }

  @Override
  public Either<? extends ErrorResponse, GetCommentsResponse> process(GetCommentsRequest input) {
    return validateInput(input)
        .flatMap(validInput ->
            Try.of(() -> {
                  return GetCommentsResponse.builder().build();
                })
                .toEither()
                .mapLeft(t -> Match(t).of(
                    defaultCase(t)
                ))
        );
  }
}
