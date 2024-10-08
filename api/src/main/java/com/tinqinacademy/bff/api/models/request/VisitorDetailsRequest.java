package com.tinqinacademy.bff.api.models.request;

import com.tinqinacademy.bff.api.validation.annotations.DatesMatch;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
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
@DatesMatch(
    startField = "startDate",
    endField = "endDate",
    message = "Start date must be fore end date"
)
public class VisitorDetailsRequest {

  @FutureOrPresent(message = "Start date must be in the future")
  @Schema(example = "2025-10-10")
  private LocalDate startDate;

  @FutureOrPresent(message = "Start date must be in the future")
  @Schema(example = "2025-11-10")
  private LocalDate endDate;

  @NotBlank(message = "First name cannot be blank")
  @Size(min = 2, max = 40, message = "First name has to be between 2 and 40 characters")
  @Schema(example = "John")
  private String firstName;

  @NotBlank(message = "Last name cannot be blank")
  @Size(min = 2, max = 40, message = "Last name has to be between 2 and 40 characters")
  @Schema(example = "Doe")
  private String lastName;

  @NotBlank(message = "Phone number cannot be blank")
  @Size(min = 10, max = 16, message = "Invalid number format")
  @Schema(example = "+359 984371483")
  private String phoneNo;

  @NotBlank(message = "Id card number must not be blank")
  @Size(min = 8, max = 15, message = "Id card number must be between 8 and 15 characters")
  @Schema(defaultValue = "12341222")
  private String idCardNo;

  @Past(message = "Birth date cannot be a future date")
  @Schema(example = "2004-10-10")
  private LocalDate birthDate;

  @Future(message = "Card validity cannot be expired")
  @Schema(example = "2028-10-10")
  private LocalDate idCardValidity;

  @NotBlank(message = "Card issuer cannot be blank")
  @Size(min = 1, max = 30, message = "Card issuer must be between 1 and 30 characters")
  @Schema(example = "MVR RAZGRAD")
  private String idCardIssueAuthority;

  @Past(message = "Id card issued date must be valid")
  @Schema(example = "2023-10-10")
  private LocalDate idCardIssueDate;
}
