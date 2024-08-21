package com.tinqinacademy.bff.api.operations.deletecomment.request;

import com.tinqinacademy.bff.api.base.OperationRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DeleteCommentRequest implements OperationRequest {

  @UUID(message = "Comment id has to be a valid UUID string")
  @NotBlank(message = "Comment id cannot be blank")
  private String commentId;
}
