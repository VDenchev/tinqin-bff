package com.tinqinacademy.bff.core.processors;

import com.tinqinacademy.bff.api.errors.ErrorResponse;
import com.tinqinacademy.bff.api.operations.deleteroom.operation.DeleteRoomOperation;
import com.tinqinacademy.bff.api.operations.deleteroom.request.DeleteRoomRequest;
import com.tinqinacademy.bff.api.operations.deleteroom.response.DeleteRoomResponse;
import com.tinqinacademy.hotel.api.operations.deleteroom.output.DeleteRoomOutput;
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
public class DeleteRoomOperationProcessor extends BaseOperationProcessor implements DeleteRoomOperation {

  private final HotelClient hotelClient;

  public DeleteRoomOperationProcessor(
      ConversionService conversionService, Validator validator,
      HotelClient hotelClient
  ) {
    super(conversionService, validator);
    this.hotelClient = hotelClient;
  }

  @Override
  public Either<? extends ErrorResponse, DeleteRoomResponse> process(DeleteRoomRequest request) {
    return validateInput(request)
        .flatMap(validRequest ->
            Try.of(() -> {
                  log.info("Start delete room input: {}", validRequest);

                  DeleteRoomOutput output = hotelClient.deleteRoom(validRequest.getId());

                  DeleteRoomResponse response = createResponse(output);
                  log.info("End delete room output: {}", validRequest);
                  return response;
                })
                .toEither()
                .mapLeft(t -> Match(t).of(
                    feignCase(),
                    defaultCase(t)
                ))
        );
  }

  private DeleteRoomResponse createResponse(DeleteRoomOutput output) {
    return DeleteRoomResponse.builder()
        .build();
  }
}
