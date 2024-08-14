package com.tinqinacademy.bff.api.operations.updatecomment.updatecommentbyadmin.response;

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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UpdateCommentByAdminResponse implements OperationResponse {

  @JsonUnwrapped
  private CommentResponse commentResponse;
}
