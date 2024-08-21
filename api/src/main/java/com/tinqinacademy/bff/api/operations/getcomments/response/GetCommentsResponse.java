package com.tinqinacademy.bff.api.operations.getcomments.response;

import com.tinqinacademy.bff.api.base.OperationResponse;
import com.tinqinacademy.bff.api.models.response.CommentDetailsResponse;
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
public class GetCommentsResponse implements OperationResponse {

  private List<CommentDetailsResponse> comments;
  private Integer pageNumber;
  private Integer totalPages;
  private Long totalElements;
  private Integer pageSize;
  private Integer numberOfElements;
  private Boolean empty;
}
