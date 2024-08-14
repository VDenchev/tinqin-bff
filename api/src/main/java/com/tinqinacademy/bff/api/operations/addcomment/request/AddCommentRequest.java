package com.tinqinacademy.bff.api.operations.addcomment.request;

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
public class AddCommentRequest implements OperationRequest {

  @JsonIgnore
  @UUID(message = "Room id has to be a valid UUID string")
  private String roomId;

  @NotBlank
  @Size(min = 2, max = 40)
  @Schema(example = "Dimcho")
  private String firstName;

  @NotBlank
  @Size(min = 2, max = 40)
  @Schema(example = "Yasenov")
  private String lastName;

  @NotBlank
  @Size(max = 1_000)
  @Schema(example = "Very good room, I would recommend to all my friends!")
  private String content;
}
