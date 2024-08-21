package com.tinqinacademy.bff.api.operations.updatecomment.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.bff.api.base.OperationRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class UpdateCommentRequest implements OperationRequest {

  @JsonIgnore
  @UUID(message = "Comment id has to be a valid UUID string")
  @NotBlank(message = "Commend id cannot be blank")
  private String commentId;

  @JsonIgnore
  @UUID(message = "User id has to be a valid UUID string")
  @NotBlank(message = "User id cannot be blank")
  private String userId;

  @NotBlank
  @Size(max = 1_000, message = "Content must be a maximum of 1000 character long")
  @Schema(example = "I changed my mind. This hotel is trash")
  private String content;
}
