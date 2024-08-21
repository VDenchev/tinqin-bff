package com.tinqinacademy.bff.core.converters;

import com.tinqinacademy.bff.api.models.response.CommentDetailsResponse;
import com.tinqinacademy.bff.core.converters.base.BaseConverter;
import com.tinqinacademy.comments.api.models.output.CommentDetailsOutput;
import org.springframework.stereotype.Component;

@Component
public class CommentDetailsOutputToCommentDetailsResponse extends BaseConverter<CommentDetailsOutput, CommentDetailsResponse> {

  @Override
  protected CommentDetailsResponse doConvert(CommentDetailsOutput source) {
    return CommentDetailsResponse.builder()
        .id(source.getId())
        .firstName(source.getFirstName())
        .lastName(source.getLastName())
        .content(source.getContent())
        .publishedBy(source.getPublishedBy())
        .publishDate(source.getPublishDate())
        .lastEditedDate(source.getLastEditedDate())
        .lastEditedBy(source.getLastEditedBy())
        .build();
  }
}
