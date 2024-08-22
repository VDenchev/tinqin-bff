package com.tinqinacademy.bff.api.models.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class VisitorDetailsResponse {

  @Schema(example = "2024-05-12")
  private LocalDate startDate;

  @Schema(example = "2024-07-12")
  private LocalDate endDate;

  @Schema(example = "Fernando")
  private String firstName;

  @Schema(example = "Alonso")
  private String lastName;

  @Schema(example = "9205127401")
  private String idCardNo;

  @Schema(example = "1992-05-12")
  private LocalDate birthDate;

  @Schema(example = "2028-05-20")
  private LocalDate idCardValidity;

  @Schema(example = "MVR RAZGRAD")
  private String idCardIssueAuthority;

  @Schema(example = "2018-05-20")
  private LocalDate idCardIssueDate;
}
