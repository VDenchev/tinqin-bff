package com.tinqinacademy.bff.api.operations.searchvisitors.request;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.tinqinacademy.bff.api.base.OperationRequest;
import com.tinqinacademy.bff.api.models.request.VisitorDetailsRequest;
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
@ToString
@Builder
public class SearchVisitorsRequest implements OperationRequest {

  @JsonUnwrapped
  private VisitorDetailsRequest visitorDetailsRequest;

  private String roomNo;
}
