package com.tinqinacademy.bff.api.operations.getcomments.request;

import com.tinqinacademy.bff.api.base.OperationRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GetCommentsRequest implements OperationRequest {

  @Schema(example = "Room UUID")
  @UUID(message = "Room id has to be a valid UUID string")
  @NotBlank(message = "Room id must not be blank")
  private String roomId;

  @Schema(example = "0")
  @PositiveOrZero(message = "The page cannot be a negative number")
  private Integer pageNumber;

  @Schema(example = "0")
  @PositiveOrZero(message = "The page size cannot be a negative number")
  private Integer pageSize;
}
