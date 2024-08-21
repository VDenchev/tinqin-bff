package com.tinqinacademy.bff.core.converters;

import com.tinqinacademy.bff.api.operations.addcomment.request.AddCommentRequest;
import com.tinqinacademy.bff.core.converters.base.BaseConverter;
import com.tinqinacademy.comments.api.operations.addcomment.input.AddCommentInput;
import org.springframework.stereotype.Component;

@Component
public class AddCommentRequestToAddCommentInput extends BaseConverter<AddCommentRequest, AddCommentInput> {

  @Override
  protected AddCommentInput doConvert(AddCommentRequest source) {
    return AddCommentInput.builder()
        .roomId(source.getRoomId())
        .userId(source.getUserId())
        .content(source.getContent())
        .firstName(source.getFirstName())
        .lastName(source.getLastName())
        .build();
  }
}
