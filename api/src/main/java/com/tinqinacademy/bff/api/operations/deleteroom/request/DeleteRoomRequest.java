package com.tinqinacademy.bff.api.operations.deleteroom.request;

import com.tinqinacademy.bff.api.base.OperationRequest;
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
public class DeleteRoomRequest implements OperationRequest {

  @UUID(message = "Id has to be a valid UUID string")
  @NotBlank(message = "Room id cannot be blank")
  private String id;
}
