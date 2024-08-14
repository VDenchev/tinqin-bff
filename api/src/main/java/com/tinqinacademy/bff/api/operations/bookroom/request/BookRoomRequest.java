package com.tinqinacademy.bff.api.operations.bookroom.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.bff.api.base.OperationRequest;
import com.tinqinacademy.bff.api.validation.annotations.DatesMatch;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.UUID;

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
    message = "Start date must be before endDate"
)
public class BookRoomRequest implements OperationRequest {

  @JsonIgnore
  @UUID(message = "RoomId has to be a valid UUID string")
  private String roomId;

  @NotNull(message = "Start date cannot be null")
  @FutureOrPresent(message = "Start date must be a future date")
  @Schema(example = "2025-10-10")
  private LocalDate startDate;

  @NotNull(message = "End date cannot be null")
  @FutureOrPresent(message = "End date must be a future date")
  @Schema(example = "2025-10-11")
  private LocalDate endDate;

  @NotBlank(message = "Phone number cannot be blank")
  @Size(min = 10, max = 15, message = "Invalid phone number format")
  @Schema(example = "+359 972947321")
  private String phoneNumber;

  @NotBlank(message = "First name cannot not be blank")
  @Size(min = 2, max = 40, message = "Last name must be between 2 and 40 characters long")
  @Schema(example = "John")
  private String firstName;

  @NotBlank(message = "Last name cannot not be blank")
  @Size(min = 2, max = 40, message = "Last name must be between 2 and 40 characters long")
  @Schema(example = "Doe")
  private String lastName;
}
