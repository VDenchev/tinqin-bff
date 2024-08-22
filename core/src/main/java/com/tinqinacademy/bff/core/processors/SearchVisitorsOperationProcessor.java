package com.tinqinacademy.bff.core.processors;

import com.tinqinacademy.authentication.api.operations.getuserbyphoneno.output.SearchUsersOutput;
import com.tinqinacademy.authentication.restexport.AuthClient;
import com.tinqinacademy.bff.api.errors.ErrorResponse;
import com.tinqinacademy.bff.api.models.request.VisitorDetailsRequest;
import com.tinqinacademy.bff.api.models.response.VisitorDetailsResponse;
import com.tinqinacademy.bff.api.operations.searchvisitors.operation.SearchVisitorsOperation;
import com.tinqinacademy.bff.api.operations.searchvisitors.request.SearchVisitorsRequest;
import com.tinqinacademy.bff.api.operations.searchvisitors.response.SearchVisitorsResponse;
import com.tinqinacademy.hotel.api.models.input.VisitorDetailsInput;
import com.tinqinacademy.hotel.api.operations.searchvisitors.input.SearchVisitorsInput;
import com.tinqinacademy.hotel.api.operations.searchvisitors.output.SearchVisitorsOutput;
import com.tinqinacademy.hotel.restexport.client.HotelClient;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.vavr.API.Match;

@Slf4j
@Service
public class SearchVisitorsOperationProcessor extends BaseOperationProcessor implements SearchVisitorsOperation {

  private final AuthClient authClient;
  private final HotelClient hotelClient;

  public SearchVisitorsOperationProcessor(ConversionService conversionService, Validator validator, AuthClient authClient, HotelClient hotelClient) {
    super(conversionService, validator);
    this.authClient = authClient;
    this.hotelClient = hotelClient;
  }

  @Override
  public Either<? extends ErrorResponse, SearchVisitorsResponse> process(SearchVisitorsRequest request) {
    return validateInput(request)
        .flatMap(validRequest ->
            Try.of(() -> {
                  log.info("Start search visitors input: {}", validRequest);

                  VisitorDetailsRequest visitorDetailsRequest = validRequest.getVisitorDetailsRequest();

                  List<String> userIds = new ArrayList<>();
                  if (!StringUtils.defaultString(visitorDetailsRequest.getPhoneNo()).isBlank()) {
                    SearchUsersOutput searchUsersOutput = authClient.searchUsers(visitorDetailsRequest.getPhoneNo());
                    userIds = searchUsersOutput.getUserIds();
                  }

                  SearchVisitorsOutput output = hotelClient.searchVisitors(
                      visitorDetailsRequest.getStartDate(),
                      visitorDetailsRequest.getEndDate(),
                      visitorDetailsRequest.getBirthDate(),
                      visitorDetailsRequest.getFirstName(),
                      visitorDetailsRequest.getLastName(),
                      userIds,
                      visitorDetailsRequest.getIdCardValidity(),
                      visitorDetailsRequest.getIdCardNo(),
                      visitorDetailsRequest.getIdCardIssueDate(),
                      visitorDetailsRequest.getIdCardIssueAuthority(),
                      validRequest.getRoomNo());

                  SearchVisitorsResponse response = createResponse(output);
                  log.info("End search visitors output: {}", response);
                  return response;
                })
                .toEither()
                .mapLeft(t -> Match(t).of(
                    defaultCase(t)
                ))
        );
  }

  private SearchVisitorsResponse createResponse(SearchVisitorsOutput output) {
    List<VisitorDetailsResponse> visitorsList = output.getVisitors().stream()
        .map(v -> conversionService.convert(v, VisitorDetailsResponse.class))
        .toList();
    SearchVisitorsResponse response = SearchVisitorsResponse.builder()
        .visitors(visitorsList)
        .build();
    return response;
  }
}
