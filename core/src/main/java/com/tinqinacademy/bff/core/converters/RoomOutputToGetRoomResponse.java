package com.tinqinacademy.bff.core.converters;

import com.tinqinacademy.bff.api.operations.getroom.response.GetRoomResponse;
import com.tinqinacademy.bff.core.converters.base.BaseConverter;
import com.tinqinacademy.hotel.api.enums.BedType;
import com.tinqinacademy.hotel.api.models.output.RoomOutput;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoomOutputToGetRoomResponse extends BaseConverter<RoomOutput, GetRoomResponse> {

  @Override
  protected GetRoomResponse doConvert(RoomOutput source) {
    List<String> bedSizes = source.getBedSizes().stream().map(BedType::getCode).toList();
    return GetRoomResponse.builder()
        .id(source.getId())
        .roomNo(source.getNumber())
        .bathroomType(source.getBathroomType().getCode())
        .bedSizes(bedSizes)
        .floor(source.getFloor())
        .price(source.getPrice())
        .bedCount(source.getBedCount())
        .datesOccupied(source.getDatesOccupied())
        .build();
  }
}
