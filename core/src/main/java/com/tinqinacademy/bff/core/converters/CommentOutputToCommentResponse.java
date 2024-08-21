package com.tinqinacademy.bff.core.converters;

import com.tinqinacademy.bff.api.models.response.CommentResponse;
import com.tinqinacademy.bff.core.converters.base.BaseConverter;
import com.tinqinacademy.comments.api.models.output.CommentOutput;
import org.springframework.stereotype.Component;

@Component
public class CommentOutputToCommentResponse extends BaseConverter<CommentOutput, CommentResponse> {
  @Override
  protected CommentResponse doConvert(CommentOutput source) {
    return CommentResponse.builder()
        .id(source.getId())
        .build();
  }
}
