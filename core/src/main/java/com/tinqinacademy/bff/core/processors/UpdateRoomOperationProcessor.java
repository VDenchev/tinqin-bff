package com.tinqinacademy.bff.core.processors;

import com.tinqinacademy.bff.api.errors.ErrorResponse;
import com.tinqinacademy.bff.api.operations.updateroom.operation.UpdateRoomOperation;
import com.tinqinacademy.bff.api.operations.updateroom.request.UpdateRoomRequest;
import com.tinqinacademy.bff.api.operations.updateroom.response.UpdateRoomResponse;
import com.tinqinacademy.hotel.api.models.input.RoomInput;
import com.tinqinacademy.hotel.api.operations.updateroom.input.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.updateroom.output.UpdateRoomOutput;
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
public class UpdateRoomOperationProcessor extends BaseOperationProcessor implements UpdateRoomOperation {

  private final HotelClient hotelClient;

  public UpdateRoomOperationProcessor(
      ConversionService conversionService, Validator validator,
      HotelClient hotelClient
  ) {
    super(conversionService, validator);
    this.hotelClient = hotelClient;
  }

  @Override
  public Either<? extends ErrorResponse, UpdateRoomResponse> process(UpdateRoomRequest request) {
    return validateInput(request)
        .flatMap(validRequest ->
            Try.of(() -> {
                  log.info("Start update room input: {}", validRequest);

                  UpdateRoomInput updateRoomInput = convertToUpdateRoomInput(validRequest);
                  UpdateRoomOutput output = hotelClient.updateRoom(validRequest.getRoomId(), updateRoomInput);

                  UpdateRoomResponse response = createResponse(output);
                  log.info("End update room output: {}", response);
                  return response;
                })
                .toEither()
                .mapLeft(t -> Match(t).of(
                    feignCase(),
                    defaultCase(t)
                ))
        );
  }

  private UpdateRoomResponse createResponse(UpdateRoomOutput output) {
    UpdateRoomResponse response = UpdateRoomResponse.builder()
        .id(output.getId())
        .build();
    return response;
  }

  private UpdateRoomInput convertToUpdateRoomInput(UpdateRoomRequest request) {
    RoomInput roomInput = conversionService.convert(request.getRoomDetailsRequest(), RoomInput.class);
    UpdateRoomInput updateRoomInput = UpdateRoomInput.builder()
        .roomInput(roomInput)
        .build();
    return updateRoomInput;
  }
}
