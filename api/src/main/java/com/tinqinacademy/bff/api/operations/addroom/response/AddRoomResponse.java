package com.tinqinacademy.bff.api.operations.addroom.response;

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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AddRoomResponse implements OperationResponse {

  @Schema(example = "Id of the added room")
  private String id;
}
