package com.tinqinacademy.bff.api.operations.searchvisitors.response;

import com.tinqinacademy.bff.api.base.OperationResponse;
import com.tinqinacademy.bff.api.models.response.VisitorDetailsResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SearchVisitorsResponse implements OperationResponse {

  private List<VisitorDetailsResponse> visitors;
}
