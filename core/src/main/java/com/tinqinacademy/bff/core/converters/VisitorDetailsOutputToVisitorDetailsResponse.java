package com.tinqinacademy.bff.core.converters;

import com.tinqinacademy.bff.api.models.response.VisitorDetailsResponse;
import com.tinqinacademy.bff.core.converters.base.BaseConverter;
import com.tinqinacademy.hotel.api.models.output.VisitorDetailsOutput;
import org.springframework.stereotype.Component;

@Component
public class VisitorDetailsOutputToVisitorDetailsResponse extends BaseConverter<VisitorDetailsOutput, VisitorDetailsResponse> {
  @Override
  protected VisitorDetailsResponse doConvert(VisitorDetailsOutput source) {
    return VisitorDetailsResponse.builder()
        .startDate(source.getStartDate())
        .endDate(source.getEndDate())
        .birthDate(source.getBirthDate())
        .firstName(source.getFirstName())
        .lastName(source.getLastName())
        .idCardIssueAuthority(source.getIdCardIssueAuthority())
        .idCardIssueDate(source.getIdCardIssueDate())
        .idCardNo(source.getIdCardNo())
        .idCardValidity(source.getIdCardValidity())
        .build();
  }
}
