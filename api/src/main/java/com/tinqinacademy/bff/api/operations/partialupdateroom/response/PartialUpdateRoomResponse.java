package com.tinqinacademy.bff.api.operations.partialupdateroom.response;

import com.tinqinacademy.bff.api.base.OperationResponse;
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
public class PartialUpdateRoomResponse implements OperationResponse {

  private String id;
}
