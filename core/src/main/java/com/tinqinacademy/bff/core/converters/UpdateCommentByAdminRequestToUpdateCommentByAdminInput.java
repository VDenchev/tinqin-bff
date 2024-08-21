package com.tinqinacademy.bff.core.converters;

import com.tinqinacademy.bff.api.operations.updatecommentbyadmin.request.UpdateCommentByAdminRequest;
import com.tinqinacademy.bff.core.converters.base.BaseConverter;
import com.tinqinacademy.comments.api.operations.updatecommentbyadmin.input.UpdateCommentByAdminInput;
import org.springframework.stereotype.Component;

@Component
public class UpdateCommentByAdminRequestToUpdateCommentByAdminInput extends BaseConverter<UpdateCommentByAdminRequest, UpdateCommentByAdminInput> {
  @Override
  protected UpdateCommentByAdminInput doConvert(UpdateCommentByAdminRequest source) {
    return UpdateCommentByAdminInput.builder()
        .adminId(source.getUserId())
        .content(source.getContent())
        .firstName(source.getFirstName())
        .lastName(source.getLastName())
        .build();
  }
}
