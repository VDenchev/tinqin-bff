package com.tinqinacademy.bff.api.models.request;

import com.tinqinacademy.bff.api.enums.BathroomType;
import com.tinqinacademy.bff.api.enums.BedType;
import com.tinqinacademy.bff.api.validation.annotations.ValidEnum;
import com.tinqinacademy.bff.api.validation.groups.NonMandatoryFieldsGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RoomDetailsRequest {

  @NotNull(message = "Bed size cannot be null")
  @Schema(example = "[\"single\", \"double\"]")
  private List<
      @ValidEnum(enumClass = BedType.class, groups = {NonMandatoryFieldsGroup.class, Default.class}, message = "Invalid bed size")
      @NotBlank(message = "Bed size cannot be blank", groups = {NonMandatoryFieldsGroup.class, Default.class})
          String> bedSizes;

  @NotNull(message = "Room number must not be null")
  @Size(
      groups = {NonMandatoryFieldsGroup.class, Default.class},
      message = "Room number should be at most 10 characters long",
      min = 1, max = 10
  )
  @Schema(example = "101A")
  private String roomNo;

  @NotNull(message = "Floor cannot be null")
  @Schema(example = "1")
  private Integer floor;

  @NotNull(message = "Price cannot be null")
  @Positive(
      groups = {NonMandatoryFieldsGroup.class, Default.class},
      message = "Price has to be a positive number"
  )
  @Schema(example = "150")
  private BigDecimal price;

  @NotNull(message = "Bathroom type cannot be null")
  @Size(min = 1, message = "Bathroom type must not be empty", groups = {NonMandatoryFieldsGroup.class, Default.class})
  @Schema(example = "private")
  @ValidEnum(enumClass = BathroomType.class, groups = {NonMandatoryFieldsGroup.class, Default.class}, message = "Invalid bathroom type")
  private String bathroomType;
}
