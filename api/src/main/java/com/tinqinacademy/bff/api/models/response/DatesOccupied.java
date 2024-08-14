package com.tinqinacademy.bff.api.models.response;

import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DatesOccupied {

  @JsonValue
  @Schema(example = "[\"2024-07-12\", \"2024-07-13\"]")
  private List<LocalDate> dates;
}
