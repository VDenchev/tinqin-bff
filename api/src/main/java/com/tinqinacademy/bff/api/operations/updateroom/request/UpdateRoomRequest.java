package com.tinqinacademy.bff.api.operations.updateroom.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import org.hibernate.validator.constraints.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UpdateRoomRequest implements OperationRequest {

  @JsonUnwrapped
  @Valid
  private RoomDetailsRequest roomDetailsRequest;

  @JsonIgnore
  @UUID(message = "RoomId has to be a valid UUID string")
  private String roomId;
}
