package com.tinqinacademy.bff.api.operations.checkavailablerooms.response;

import com.tinqinacademy.bff.api.base.OperationResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CheckAvailableRoomsResponse implements OperationResponse {

  @Schema(example = "[\"uuid1\", \"uuid2\"]")
  private List<String> roomIds;
}
