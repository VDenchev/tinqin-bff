package com.tinqinacademy.bff.core.processors;

import com.tinqinacademy.bff.api.errors.ErrorResponse;
import com.tinqinacademy.bff.api.operations.registervisitors.operation.RegisterVisitorsOperation;
import com.tinqinacademy.bff.api.operations.registervisitors.request.RegisterVisitorsRequest;
import com.tinqinacademy.bff.api.operations.registervisitors.response.RegisterVisitorsResponse;
import com.tinqinacademy.hotel.api.models.input.VisitorDetailsInput;
import com.tinqinacademy.hotel.api.operations.registervisitors.input.RegisterVisitorsInput;
import com.tinqinacademy.hotel.api.operations.registervisitors.output.RegisterVisitorsOutput;
import com.tinqinacademy.hotel.restexport.client.HotelClient;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.vavr.API.Match;

@Slf4j
@Service
public class RegisterVisitorsOperationProcessor extends BaseOperationProcessor implements RegisterVisitorsOperation {

  private final HotelClient hotelClient;

  public RegisterVisitorsOperationProcessor(ConversionService conversionService, Validator validator, HotelClient hotelClient) {
    super(conversionService, validator);
    this.hotelClient = hotelClient;
  }

  @Override
  public Either<? extends ErrorResponse, RegisterVisitorsResponse> process(RegisterVisitorsRequest request) {
    return validateInput(request)
        .flatMap(validRequest ->
            Try.of(() -> {
                  log.info("Start register visitors input: {}", validRequest);

                  RegisterVisitorsInput input = convertToInput(request);

                  RegisterVisitorsOutput output = hotelClient.registerVisitors(validRequest.getBookingId(), input);

                  RegisterVisitorsResponse response = createResponse(output);
                  log.info("End register visitors output: {}", response);
                  return response;
                })
                .toEither()
                .mapLeft(t -> Match(t).of(
                    defaultCase(t)
                ))
        );
  }

  private RegisterVisitorsInput convertToInput(RegisterVisitorsRequest request) {
    List<VisitorDetailsInput> visitors = request.getVisitors().stream()
        .map(v -> conversionService.convert(v, VisitorDetailsInput.class))
        .toList();

    RegisterVisitorsInput input = RegisterVisitorsInput.builder()
        .bookingId(request.getBookingId())
        .visitors(visitors)
        .build();
    return input;
  }

  private static RegisterVisitorsResponse createResponse(RegisterVisitorsOutput output) {
    return RegisterVisitorsResponse.builder().build();
  }
}
