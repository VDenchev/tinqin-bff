package com.tinqinacademy.bff.core.converters;

import com.tinqinacademy.bff.api.operations.getcomments.response.GetCommentsResponse;
import com.tinqinacademy.bff.core.converters.base.BaseConverter;
import com.tinqinacademy.comments.api.operations.getcomments.output.GetCommentsOutput;
import org.springframework.stereotype.Component;

@Component
public class GetCommentsOutputToGetCommentsResponse extends BaseConverter<GetCommentsOutput, GetCommentsResponse> {

  @Override
  protected GetCommentsResponse doConvert(GetCommentsOutput source) {
    return GetCommentsResponse.builder()
        .totalPages(source.getTotalPages())
        .totalElements(source.getTotalElements())
        .pageNumber(source.getPageNumber())
        .pageSize(source.getPageSize())
        .numberOfElements(source.getNumberOfElements())
        .empty(source.getEmpty())
        .build();
  }
}
