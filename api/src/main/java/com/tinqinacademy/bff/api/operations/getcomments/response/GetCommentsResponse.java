package com.tinqinacademy.bff.api.operations.getcomments.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.tinqinacademy.bff.api.base.OperationResponse;
import com.tinqinacademy.bff.api.models.response.CommentDetailsResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GetCommentsResponse implements OperationResponse {

  @JsonUnwrapped
  private Page<CommentDetailsResponse> page;
}
