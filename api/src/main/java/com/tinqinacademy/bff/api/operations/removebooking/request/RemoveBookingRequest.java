package com.tinqinacademy.bff.api.operations.removebooking.request;

import com.tinqinacademy.bff.api.base.OperationRequest;
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

  @UUID(message = "BookingId has to be a valid UUID string")
  private String bookingId;
}
