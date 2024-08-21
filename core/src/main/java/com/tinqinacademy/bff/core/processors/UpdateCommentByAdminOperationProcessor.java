package com.tinqinacademy.bff.core.processors;

import com.tinqinacademy.bff.api.errors.ErrorResponse;
import com.tinqinacademy.bff.api.models.response.CommentResponse;
import com.tinqinacademy.bff.api.operations.updatecommentbyadmin.operation.UpdateCommentByAdminOperation;
import com.tinqinacademy.bff.api.operations.updatecommentbyadmin.request.UpdateCommentByAdminRequest;
import com.tinqinacademy.bff.api.operations.updatecommentbyadmin.response.UpdateCommentByAdminResponse;
import com.tinqinacademy.comments.api.operations.updatecommentbyadmin.input.UpdateCommentByAdminInput;
import com.tinqinacademy.comments.api.operations.updatecommentbyadmin.output.UpdateCommentByAdminOutput;
import com.tinqinacademy.comments.restexport.client.CommentsClient;
import com.tinqinacademy.hotel.api.operations.getroombyroomno.output.GetRoomByRoomNoOutput;
import com.tinqinacademy.hotel.restexport.client.HotelClient;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import static io.vavr.API.Match;

@Slf4j
@Service
public class UpdateCommentByAdminOperationProcessor extends BaseOperationProcessor implements UpdateCommentByAdminOperation {

  private final HotelClient hotelClient;
  private final CommentsClient commentsClient;

  public UpdateCommentByAdminOperationProcessor(
      ConversionService conversionService, Validator validator,
      HotelClient hotelClient, CommentsClient commentsClient) {
    super(conversionService, validator);
    this.hotelClient = hotelClient;
    this.commentsClient = commentsClient;
  }

  @Override
  public Either<? extends ErrorResponse, UpdateCommentByAdminResponse> process(UpdateCommentByAdminRequest request) {
    return validateInput(request)
        .flatMap(validRequest ->
            Try.of(() -> {
                  log.info("Start update comment by admin input: {}", validRequest);

                  GetRoomByRoomNoOutput roomOutput = hotelClient.getRoomByRoomNo(
                      validRequest.getRoomNo());

                  UpdateCommentByAdminInput input = convertToCommentInput(validRequest, roomOutput);

                  UpdateCommentByAdminOutput output = commentsClient.updateCommentByAdmin(
                      validRequest.getCommentId(), input);

                  UpdateCommentByAdminResponse response = createResponse(output);
                  log.info("End update comment by admin output: {}", response);
                  return response;
                })
                .toEither()
                .mapLeft(t -> Match(t).of(
                    feignCase(),
                    defaultCase(t)
                ))
        );
  }

  private UpdateCommentByAdminInput convertToCommentInput(UpdateCommentByAdminRequest validRequest, GetRoomByRoomNoOutput roomOutput) {
    UpdateCommentByAdminInput input = conversionService.convert(validRequest, UpdateCommentByAdminInput.class);
    input.setRoomId(roomOutput.getRoomOutput().getId());
    return input;
  }

  private UpdateCommentByAdminResponse createResponse(UpdateCommentByAdminOutput output) {
    CommentResponse commentResponse = conversionService.convert(output.getOutput(), CommentResponse.class);
    UpdateCommentByAdminResponse response = UpdateCommentByAdminResponse.builder()
        .commentResponse(commentResponse)
        .build();
    return response;
  }
}
