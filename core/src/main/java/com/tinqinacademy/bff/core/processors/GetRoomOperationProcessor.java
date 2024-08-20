package com.tinqinacademy.bff.core.processors;

import com.tinqinacademy.bff.api.errors.ErrorResponse;
import com.tinqinacademy.bff.api.operations.getroom.operation.GetRoomOperation;
import com.tinqinacademy.bff.api.operations.getroom.request.GetRoomRequest;
import com.tinqinacademy.bff.api.operations.getroom.response.GetRoomResponse;
import com.tinqinacademy.hotel.api.operations.getroom.output.RoomDetailsOutput;
import com.tinqinacademy.hotel.restexport.client.HotelClient;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import static io.vavr.API.Match;

@Slf4j
@Service
public class GetRoomOperationProcessor extends BaseOperationProcessor implements GetRoomOperation {

  private final HotelClient hotelClient;

  public GetRoomOperationProcessor(
      ConversionService conversionService, Validator validator,
      HotelClient hotelClient
  ) {
    super(conversionService, validator);
    this.hotelClient = hotelClient;
  }

  @Override
  public Either<? extends ErrorResponse, GetRoomResponse> process(GetRoomRequest request) {
    return validateInput(request)
        .flatMap(validRequest ->
            Try.of(() -> {
                  log.info("Start get room input: {}", validRequest);

                  RoomDetailsOutput output = hotelClient.getRoom(validRequest.getRoomId());

                  GetRoomResponse response = createResponse(output);
                  log.info("End get room output: {}", response);
                  return response;
                })
                .toEither()
                .mapLeft(t -> Match(t).of(
                    feignCase(),
                    defaultCase(t)
                ))
        );
  }

  @Nullable
  private GetRoomResponse createResponse(RoomDetailsOutput roomDetailsOutput) {
    return conversionService.convert(roomDetailsOutput.getRoomOutput(), GetRoomResponse.class);
  }
}
