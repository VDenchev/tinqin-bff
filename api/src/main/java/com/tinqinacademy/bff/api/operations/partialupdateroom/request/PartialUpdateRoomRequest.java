package com.tinqinacademy.bff.api.operations.partialupdateroom.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.tinqinacademy.bff.api.base.OperationRequest;
import com.tinqinacademy.bff.api.models.request.RoomDetailsRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PartialUpdateRoomRequest implements OperationRequest {

  @JsonUnwrapped
  @Valid
  private RoomDetailsRequest roomDetailsRequest;

  @JsonIgnore
  @UUID(message = "RoomId has to be a valid UUID string")
  @NotBlank(message = "Room id cannot be blank")
  private String roomId;
}
