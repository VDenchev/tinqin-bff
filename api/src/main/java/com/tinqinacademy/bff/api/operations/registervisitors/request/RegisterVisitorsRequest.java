package com.tinqinacademy.bff.api.operations.registervisitors.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.bff.api.base.OperationRequest;
import com.tinqinacademy.bff.api.models.request.VisitorDetailsRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.UUID;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RegisterVisitorsRequest implements OperationRequest {

  @NotNull(message = "Visitors cannot be empty")
  private List<@Valid VisitorDetailsRequest> visitors;

  @JsonIgnore
  @UUID(message = "BookingId has to be a valid UUID string")
  private String bookingId;
}
