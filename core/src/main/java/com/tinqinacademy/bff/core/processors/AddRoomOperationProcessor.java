package com.tinqinacademy.bff.core.processors;

import com.tinqinacademy.bff.api.errors.ErrorResponse;
import com.tinqinacademy.bff.api.operations.addroom.operation.AddRoomOperation;
import com.tinqinacademy.bff.api.operations.addroom.request.AddRoomRequest;
import com.tinqinacademy.bff.api.operations.addroom.response.AddRoomResponse;
import com.tinqinacademy.hotel.api.models.input.RoomInput;
import com.tinqinacademy.hotel.api.operations.addroom.input.AddRoomInput;
import com.tinqinacademy.hotel.api.operations.addroom.output.AddRoomOutput;
import com.tinqinacademy.hotel.restexport.client.HotelClient;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static io.vavr.API.Match;

@Service
@Slf4j
public class AddRoomOperationProcessor extends BaseOperationProcessor implements AddRoomOperation {

  private final HotelClient hotelClient;

  public AddRoomOperationProcessor(
      ConversionService conversionService, Validator validator,
      HotelClient hotelClient
  ) {
    super(conversionService, validator);
    this.hotelClient = hotelClient;
  }

  @Override
  @Transactional
  public Either<? extends ErrorResponse, AddRoomResponse> process(AddRoomRequest request) {
    return validateInput(request)
        .flatMap(validRequest ->
            Try.of(() -> {
                  log.info("Start add room input: {}", validRequest);

                  AddRoomInput input = convertToAddRoomInput(validRequest);
                  AddRoomOutput output = hotelClient.addRoom(input);

                  AddRoomResponse response = createResponse(output);
                  log.info("End add room output: {}", response);
                  return response;
                })
                .toEither()
                .mapLeft(t -> Match(t).of(
                    feignCase(),
                    defaultCase(t)
                ))
        );
  }

  private AddRoomInput convertToAddRoomInput(AddRoomRequest validRequest) {
    RoomInput roomInput = conversionService.convert(validRequest.getRoomDetailsRequest(), RoomInput.class);
    AddRoomInput addRoomInput = AddRoomInput.builder()
        .roomInput(roomInput)
        .build();
    return addRoomInput;
  }

  private AddRoomResponse createResponse(AddRoomOutput addRoomOutput) {
    AddRoomResponse response = AddRoomResponse.builder()
        .id(addRoomOutput.getId())
        .build();
    return response;
  }
}
