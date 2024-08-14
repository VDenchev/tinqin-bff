package com.tinqinacademy.bff.api.operations.deleteroom.request;

import com.fasterxml.jackson.annotation.JsonValue;
import com.tinqinacademy.bff.api.base.OperationRequest;
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
public class DeleteRoomRequest implements OperationRequest {

  @UUID(message = "Id has to be a valid UUID string")
  private String id;
}
