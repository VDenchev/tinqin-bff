package com.tinqinacademy.bff.core.converters;

import com.tinqinacademy.bff.api.operations.updatecomment.request.UpdateCommentRequest;
import com.tinqinacademy.bff.core.converters.base.BaseConverter;
import com.tinqinacademy.comments.api.operations.updatecomment.input.UpdateCommentInput;
import org.springframework.stereotype.Component;

@Component
public class UpdateCommentRequestToUpdateCommentInput extends BaseConverter<UpdateCommentRequest, UpdateCommentInput> {

  @Override
  protected UpdateCommentInput doConvert(UpdateCommentRequest source) {
    return UpdateCommentInput.builder()
        .commentId(source.getCommentId())
        .userId(source.getUserId())
        .content(source.getContent())
        .build();
  }
}
