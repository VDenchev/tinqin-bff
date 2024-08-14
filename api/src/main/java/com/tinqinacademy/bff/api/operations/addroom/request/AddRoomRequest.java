package com.tinqinacademy.bff.api.operations.addroom.request;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.tinqinacademy.bff.api.base.OperationRequest;
import com.tinqinacademy.bff.api.models.request.RoomDetailsRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AddRoomRequest implements OperationRequest {

  @JsonUnwrapped
  @Valid
  private RoomDetailsRequest roomDetailsRequest;
}
