package com.tinqinacademy.bff.api.operations.updateroom.response;

import com.tinqinacademy.bff.api.base.OperationResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UpdateRoomResponse implements OperationResponse {

  @Schema(example = "Id of the updated room")
  private String id;
}
