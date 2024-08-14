package com.tinqinacademy.bff.api.operations.addcomment.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.tinqinacademy.bff.api.base.OperationResponse;
import com.tinqinacademy.bff.api.models.response.CommentResponse;
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
@Builder
@ToString
public class AddCommentResponse implements OperationResponse {
  @JsonUnwrapped
  private CommentResponse commentResponse;
}
