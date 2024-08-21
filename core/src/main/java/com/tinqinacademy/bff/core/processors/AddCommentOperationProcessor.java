package com.tinqinacademy.bff.core.processors;

import com.tinqinacademy.bff.api.errors.ErrorResponse;
import com.tinqinacademy.bff.api.models.response.CommentResponse;
import com.tinqinacademy.bff.api.operations.addcomment.operation.AddCommentOperation;
import com.tinqinacademy.bff.api.operations.addcomment.request.AddCommentRequest;
import com.tinqinacademy.bff.api.operations.addcomment.response.AddCommentResponse;
import com.tinqinacademy.comments.api.operations.addcomment.input.AddCommentInput;
import com.tinqinacademy.comments.api.operations.addcomment.output.AddCommentOutput;
import com.tinqinacademy.comments.restexport.client.CommentsClient;
import com.tinqinacademy.hotel.restexport.client.HotelClient;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import static io.vavr.API.Match;

@Service
@Slf4j
public class AddCommentOperationProcessor extends BaseOperationProcessor implements AddCommentOperation {

  private final CommentsClient commentsClient;
  private final HotelClient hotelClient;

  public AddCommentOperationProcessor(
      ConversionService conversionService, Validator validator,
      CommentsClient commentsClient, HotelClient hotelClient
  ) {
    super(conversionService, validator);
    this.commentsClient = commentsClient;
    this.hotelClient = hotelClient;
  }

  @Override
  public Either<? extends ErrorResponse, AddCommentResponse> process(AddCommentRequest request) {
    return validateInput(request)
        .flatMap(validRequest ->
            Try.of(() -> {
                  log.info("Start add comment input: {}", validRequest);

                  hotelClient.getRoom(validRequest.getRoomId());

                  AddCommentInput input = conversionService.convert(request, AddCommentInput.class);
                  AddCommentOutput output = commentsClient.addComment(validRequest.getRoomId(), input);

                  AddCommentResponse response = createResponse(output);
                  log.info("End add comment output: {}", response);
                  return response;
                })
                .toEither()
                .mapLeft(t -> Match(t).of(
                    feignCase(),
                    defaultCase(t)
                ))
        );
  }

  private AddCommentResponse createResponse(AddCommentOutput output) {
    CommentResponse commentResponse = conversionService.convert(output.getOutput(), CommentResponse.class);
    AddCommentResponse response = AddCommentResponse.builder()
        .commentResponse(commentResponse)
        .build();
    return response;
  }
}
