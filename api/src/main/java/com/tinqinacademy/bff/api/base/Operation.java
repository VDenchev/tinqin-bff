package com.tinqinacademy.bff.api.base;

import com.tinqinacademy.bff.api.errors.ErrorResponse;
import io.vavr.control.Either;

public interface Operation<I extends OperationRequest, O extends OperationResponse> {

  Either<? extends ErrorResponse, O> process(I input);
}
