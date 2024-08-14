package com.tinqinacademy.bff.api.operations.getroom.request;

import com.tinqinacademy.bff.api.base.OperationRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.UUID;

@Setter
@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
public class GetRoomRequest implements OperationRequest {

  @UUID(message = "Id has to be a valid UUID string")
  private String roomId;
}
