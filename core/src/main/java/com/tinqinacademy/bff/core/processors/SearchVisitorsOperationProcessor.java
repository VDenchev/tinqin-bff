package com.tinqinacademy.bff.core.processors;

import com.tinqinacademy.bff.api.errors.ErrorResponse;
import com.tinqinacademy.bff.api.operations.searchvisitors.operation.SearchVisitorsOperation;
import com.tinqinacademy.bff.api.operations.searchvisitors.request.SearchVisitorsRequest;
import com.tinqinacademy.bff.api.operations.searchvisitors.response.SearchVisitorsResponse;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import static io.vavr.API.Match;

@Service
public class SearchVisitorsOperationProcessor extends BaseOperationProcessor implements SearchVisitorsOperation {

  public SearchVisitorsOperationProcessor(ConversionService conversionService, Validator validator) {
    super(conversionService, validator);
  }

  @Override
  public Either<? extends ErrorResponse, SearchVisitorsResponse> process(SearchVisitorsRequest input) {
    return validateInput(input)
        .flatMap(validInput ->
            Try.of(() -> {
                  return SearchVisitorsResponse.builder().build();
                })
                .toEither()
                .mapLeft(t -> Match(t).of(
                    defaultCase(t)
                ))
        );
  }
}
