package com.tinqinacademy.bff.api.operations.getroom.response;

import com.tinqinacademy.bff.api.base.OperationResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GetRoomResponse implements OperationResponse {

  @Schema(example = "Id of the room")
  private String id;
  @Schema(example = "505A")
  private String roomNo;
  @Schema(example = "45.50")
  private BigDecimal price;
  @Schema(example = "1")
  private Integer floor;
  @Schema(example = "[\"single\", \"double\"]")
  private List<String> bedSizes;
  @Schema(example = "private")
  private String bathroomType;
  @Schema(example = "2")
  private Integer bedCount;
  private List<LocalDate> datesOccupied;
}
