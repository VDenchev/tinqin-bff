package com.tinqinacademy.bff.core.processors;

import com.tinqinacademy.bff.api.errors.ErrorResponse;
import com.tinqinacademy.bff.api.models.response.CommentDetailsResponse;
import com.tinqinacademy.bff.api.operations.getcomments.operation.GetCommentsOperation;
import com.tinqinacademy.bff.api.operations.getcomments.request.GetCommentsRequest;
import com.tinqinacademy.bff.api.operations.getcomments.response.GetCommentsResponse;
import com.tinqinacademy.comments.api.operations.getcomments.output.GetCommentsOutput;
import com.tinqinacademy.comments.restexport.client.CommentsClient;
import com.tinqinacademy.hotel.restexport.client.HotelClient;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.vavr.API.Match;

@Slf4j
@Service
public class GetCommentsOperationProcessor extends BaseOperationProcessor implements GetCommentsOperation {

  private final HotelClient hotelClient;
  private final CommentsClient commentsClient;

  public GetCommentsOperationProcessor(
      ConversionService conversionService, Validator validator,
      HotelClient hotelClient, CommentsClient commentsClient
  ) {
    super(conversionService, validator);
    this.hotelClient = hotelClient;
    this.commentsClient = commentsClient;
  }

  @Override
  public Either<? extends ErrorResponse, GetCommentsResponse> process(GetCommentsRequest request) {
    return validateInput(request)
        .flatMap(validRequest ->
            Try.of(() -> {
                  log.info("Start get comments input: {}", validRequest);

                  hotelClient.getRoom(validRequest.getRoomId());

                  GetCommentsOutput output = commentsClient.getComments(validRequest.getRoomId(),
                      validRequest.getPageNumber(), validRequest.getPageSize());

                  GetCommentsResponse response = createResponse(output);
                  log.info("End get comments input: {}", response);
                  return response;
                })
                .toEither()
                .mapLeft(t -> Match(t).of(
                    feignCase(),
                    defaultCase(t)
                ))
        );
  }

  private GetCommentsResponse createResponse(GetCommentsOutput output) {
    GetCommentsResponse response = conversionService.convert(output, GetCommentsResponse.class);
    List<CommentDetailsResponse> commentDetailsResponseList = output.getComments()
        .stream()
        .map(c -> conversionService.convert(c, CommentDetailsResponse.class))
        .toList();
    response.setComments(commentDetailsResponseList);
    return response;
  }
}
