package com.tinqinacademy.bff.api.operations.checkavailablerooms.request;

import com.tinqinacademy.bff.api.base.OperationRequest;
import com.tinqinacademy.bff.api.enums.BathroomType;
import com.tinqinacademy.bff.api.enums.BedType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CheckAvailableRoomsRequest implements OperationRequest {

  private Integer bedCount;
  private BathroomType bathroomType;
  private List<BedType> bedSizes;
  private LocalDate startDate;
  private LocalDate endDate;
}
