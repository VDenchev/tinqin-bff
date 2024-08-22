package com.tinqinacademy.bff.core.converters;

import com.tinqinacademy.bff.api.operations.bookroom.request.BookRoomRequest;
import com.tinqinacademy.bff.core.converters.base.BaseConverter;
import com.tinqinacademy.hotel.api.operations.bookroom.input.BookRoomInput;
import org.springframework.stereotype.Component;

@Component
public class BookRoomRequestToBookRoomInput extends BaseConverter<BookRoomRequest, BookRoomInput> {

  @Override
  protected BookRoomInput doConvert(BookRoomRequest source) {
    return BookRoomInput.builder()
        .roomId(source.getRoomId())
        .userId(source.getUserId())
        .startDate(source.getStartDate())
        .endDate(source.getEndDate())
        .firstName(source.getFirstName())
        .lastName(source.getLastName())
        .phoneNo(source.getPhoneNo())
        .build();
  }
}
