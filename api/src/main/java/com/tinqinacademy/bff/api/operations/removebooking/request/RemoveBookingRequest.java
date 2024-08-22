package com.tinqinacademy.bff.api.operations.removebooking.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RemoveBookingRequest implements OperationRequest {

  @UUID(message = "Booking id has to be a valid UUID string")
  @NotBlank(message = "Booking id must not be blank")
  private String bookingId;

  @JsonIgnore
  @UUID(message = "Booking id has to be a valid UUID string")
  @NotBlank(message = "Booking id must not be blank")
  private String userId;
}
