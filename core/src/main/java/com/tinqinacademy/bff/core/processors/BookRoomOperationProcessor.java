package com.tinqinacademy.bff.core.processors;

import com.tinqinacademy.bff.api.errors.ErrorResponse;
import com.tinqinacademy.bff.api.operations.bookroom.operation.BookRoomOperation;
import com.tinqinacademy.bff.api.operations.bookroom.request.BookRoomRequest;
import com.tinqinacademy.bff.api.operations.bookroom.response.BookRoomResponse;
import com.tinqinacademy.hotel.api.operations.bookroom.input.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.bookroom.output.BookRoomOutput;
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
public class BookRoomOperationProcessor extends BaseOperationProcessor implements BookRoomOperation {

  private final HotelClient hotelClient;

  public BookRoomOperationProcessor(ConversionService conversionService, Validator validator, HotelClient hotelClient) {
    super(conversionService, validator);
    this.hotelClient = hotelClient;
  }

  @Override
  public Either<? extends ErrorResponse, BookRoomResponse> process(BookRoomRequest request) {
    return validateInput(request)
        .flatMap(validRequest ->
            Try.of(() -> {
                  log.info("Start book room input: {}", request);
                  BookRoomInput input = conversionService.convert(validRequest, BookRoomInput.class);

                  BookRoomOutput bookRoomOutput = hotelClient.bookRoom(validRequest.getRoomId(), input);

                  BookRoomResponse response = createResponse(bookRoomOutput);
                  log.info("Start book room input: {}", response);
                  return response;
                })
                .toEither()
                .mapLeft(t -> Match(t).of(
                    feignCase(),
                    defaultCase(t)
                ))
        );
  }

  private static BookRoomResponse createResponse(BookRoomOutput bookRoomOutput) {
    BookRoomResponse response = BookRoomResponse.builder()
        .build();
    return response;
  }
}
