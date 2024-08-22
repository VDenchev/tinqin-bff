package com.tinqinacademy.bff.core.processors;

import com.tinqinacademy.bff.api.enums.BedType;
import com.tinqinacademy.bff.api.errors.ErrorResponse;
import com.tinqinacademy.bff.api.operations.checkavailablerooms.operation.CheckAvailableRoomsOperation;
import com.tinqinacademy.bff.api.operations.checkavailablerooms.request.CheckAvailableRoomsRequest;
import com.tinqinacademy.bff.api.operations.checkavailablerooms.response.CheckAvailableRoomsResponse;
import com.tinqinacademy.hotel.api.operations.checkavailablerooms.output.AvailableRoomsOutput;
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
public class CheckAvailableRoomsOperationProcessor extends BaseOperationProcessor implements CheckAvailableRoomsOperation {

  private final HotelClient hotelClient;

  public CheckAvailableRoomsOperationProcessor(ConversionService conversionService, Validator validator, HotelClient hotelClient) {
    super(conversionService, validator);
    this.hotelClient = hotelClient;
  }

  @Override
  public Either<? extends ErrorResponse, CheckAvailableRoomsResponse> process(CheckAvailableRoomsRequest request) {
    return validateInput(request)
        .flatMap(validRequest ->
            Try.of(() -> {
                  log.info("Start check available rooms input: {}", validRequest);

                  AvailableRoomsOutput output = hotelClient.checkAvailableRooms(
                      validRequest.getStartDate(),
                      validRequest.getEndDate(),
                      validRequest.getBedCount(),
                      validRequest.getBedSizes(),
                      validRequest.getBathroomType()
                  );

                  CheckAvailableRoomsResponse response = createResponse(output);
                  log.info("End check available rooms output: {}", response);
                  return response;
                })
                .toEither()
                .mapLeft(t -> Match(t).of(
                    feignCase(),
                    defaultCase(t)
                ))
        );
  }

  private static CheckAvailableRoomsResponse createResponse(AvailableRoomsOutput output) {
    CheckAvailableRoomsResponse response = CheckAvailableRoomsResponse.builder()
        .roomIds(output.getRoomIds())
        .build();
    return response;
  }
}
