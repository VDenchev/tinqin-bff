package com.tinqinacademy.bff.core.processors;

import com.tinqinacademy.bff.api.errors.ErrorResponse;
import com.tinqinacademy.bff.api.operations.partialupdateroom.operation.PartialUpdateRoomOperation;
import com.tinqinacademy.bff.api.operations.partialupdateroom.request.PartialUpdateRoomRequest;
import com.tinqinacademy.bff.api.operations.partialupdateroom.response.PartialUpdateRoomResponse;
import com.tinqinacademy.hotel.api.models.input.RoomInput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.input.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.output.PartialUpdateRoomOutput;
import com.tinqinacademy.hotel.api.validation.groups.NonMandatoryFieldsGroup;
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
public class PartialUpdateRoomOperationProcessor extends BaseOperationProcessor implements PartialUpdateRoomOperation {

  private final HotelClient hotelClient;

  public PartialUpdateRoomOperationProcessor(
      ConversionService conversionService, Validator validator, HotelClient hotelClient) {
    super(conversionService, validator);
    this.hotelClient = hotelClient;
  }

  @Override
  public Either<? extends ErrorResponse, PartialUpdateRoomResponse> process(PartialUpdateRoomRequest request) {
    return validateInput(request, NonMandatoryFieldsGroup.class)
        .flatMap(validRequest ->
            Try.of(() -> {
                  log.info("Start partial update room input: {}", validRequest);

                  PartialUpdateRoomInput input = convertToPartialInput(validRequest);
                  PartialUpdateRoomOutput output = hotelClient.partialUpdateRoom(validRequest.getRoomId(), input);

                  PartialUpdateRoomResponse response = createResponse(output);
                  log.info("End partial update room output: {}", response);
                  return response;
                })
                .toEither()
                .mapLeft(t -> Match(t).of(
                    feignCase(),
                    defaultCase(t)
                ))
        );
  }

  private PartialUpdateRoomInput convertToPartialInput(PartialUpdateRoomRequest validRequest) {
    RoomInput roomInput = conversionService.convert(validRequest.getRoomDetailsRequest(), RoomInput.class);
    PartialUpdateRoomInput input = PartialUpdateRoomInput.builder()
        .roomInput(roomInput)
        .build();
    return input;
  }

  private static PartialUpdateRoomResponse createResponse(PartialUpdateRoomOutput output) {
    PartialUpdateRoomResponse response = PartialUpdateRoomResponse.builder()
        .id(output.getId())
        .build();
    return response;
  }
}
