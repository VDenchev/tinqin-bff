package com.tinqinacademy.bff.core.converters;

import com.tinqinacademy.bff.api.models.request.RoomDetailsRequest;
import com.tinqinacademy.bff.core.converters.base.BaseConverter;
import com.tinqinacademy.hotel.api.models.input.RoomInput;
import org.springframework.stereotype.Component;

@Component
public class RoomDetailsRequestToRoomInput extends BaseConverter<RoomDetailsRequest, RoomInput> {

  @Override
  protected RoomInput doConvert(RoomDetailsRequest source) {
    return RoomInput.builder()
        .roomNo(source.getRoomNo())
        .floor(source.getFloor())
        .price(source.getPrice())
        .bedSizes(source.getBedSizes())
        .bathroomType(source.getBathroomType())
        .build();
  }
}
