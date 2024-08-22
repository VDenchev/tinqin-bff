package com.tinqinacademy.bff.core.converters;

import com.tinqinacademy.bff.api.models.request.VisitorDetailsRequest;
import com.tinqinacademy.bff.core.converters.base.BaseConverter;
import com.tinqinacademy.hotel.api.models.input.VisitorDetailsInput;
import org.springframework.stereotype.Component;

@Component
public class VisitorDetailsRequestToVisitorDetailsInput extends BaseConverter<VisitorDetailsRequest, VisitorDetailsInput> {

  @Override
  protected VisitorDetailsInput doConvert(VisitorDetailsRequest source) {
    return VisitorDetailsInput.builder()
        .startDate(source.getStartDate())
        .endDate(source.getEndDate())
        .firstName(source.getFirstName())
        .lastName(source.getLastName())
        .birthDate(source.getBirthDate())
        .idCardIssueAuthority(source.getIdCardIssueAuthority())
        .idCardIssueDate(source.getIdCardIssueDate())
        .idCardNo(source.getIdCardNo())
        .idCardValidity(source.getIdCardValidity())
        .build();
  }
}
