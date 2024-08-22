package com.tinqinacademy.bff.core.processors;

import com.tinqinacademy.bff.api.errors.ErrorResponse;
import com.tinqinacademy.bff.api.operations.removebooking.operation.RemoveBookingOperation;
import com.tinqinacademy.bff.api.operations.removebooking.request.RemoveBookingRequest;
import com.tinqinacademy.bff.api.operations.removebooking.response.RemoveBookingResponse;
import com.tinqinacademy.hotel.api.operations.removebooking.input.RemoveBookingInput;
import com.tinqinacademy.hotel.api.operations.removebooking.output.RemoveBookingOutput;
import com.tinqinacademy.hotel.restexport.client.HotelClient;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import static io.vavr.API.Match;

@Slf4j
@Service
public class RemoveBookingOperationProcessor extends BaseOperationProcessor implements RemoveBookingOperation {

  private final HotelClient hotelClient;

  public RemoveBookingOperationProcessor(ConversionService conversionService, Validator validator, HotelClient hotelClient) {
    super(conversionService, validator);
    this.hotelClient = hotelClient;
  }

  @Override
  public Either<? extends ErrorResponse, RemoveBookingResponse> process(RemoveBookingRequest request) {
    return validateInput(request)
        .flatMap(validRequest ->
            Try.of(() -> {
                  log.info("Start remove booking input: {}", validRequest);

                  RemoveBookingInput input = RemoveBookingInput.builder()
                      .userId(request.getUserId())
                      .build();
                  RemoveBookingOutput output = hotelClient.removeBooking(validRequest.getBookingId(), input);
                  RemoveBookingResponse response = createResponse(output);
                  log.info("Start remove booking output: {}", response);
                  return response;
                })
                .toEither()
                .mapLeft(t -> Match(t).of(
                    feignCase(),
                    defaultCase(t)
                ))
        );
  }

  private static RemoveBookingResponse createResponse(RemoveBookingOutput output) {
    return RemoveBookingResponse.builder().build();
  }
}
