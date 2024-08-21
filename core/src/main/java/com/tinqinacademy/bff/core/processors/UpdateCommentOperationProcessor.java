package com.tinqinacademy.bff.core.processors;

import com.tinqinacademy.bff.api.errors.ErrorResponse;
import com.tinqinacademy.bff.api.models.response.CommentResponse;
import com.tinqinacademy.bff.api.operations.updatecomment.operation.UpdateCommentOperation;
import com.tinqinacademy.bff.api.operations.updatecomment.request.UpdateCommentRequest;
import com.tinqinacademy.bff.api.operations.updatecomment.response.UpdateCommentResponse;
import com.tinqinacademy.comments.api.operations.updatecomment.input.UpdateCommentInput;
import com.tinqinacademy.comments.api.operations.updatecomment.output.UpdateCommentOutput;
import com.tinqinacademy.comments.restexport.client.CommentsClient;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import static io.vavr.API.Match;

@Slf4j
@Service
public class UpdateCommentOperationProcessor extends BaseOperationProcessor implements UpdateCommentOperation {

  private final CommentsClient commentsClient;

  public UpdateCommentOperationProcessor(
      ConversionService conversionService, Validator validator,
      CommentsClient commentsClient
  ) {
    super(conversionService, validator);
    this.commentsClient = commentsClient;
  }

  @Override
  public Either<? extends ErrorResponse, UpdateCommentResponse> process(UpdateCommentRequest request) {
    return validateInput(request)
        .flatMap(validRequest ->
            Try.of(() -> {
                  log.info("Start update comment input: {}", validRequest);

                  UpdateCommentInput input = conversionService.convert(validRequest, UpdateCommentInput.class);
                  UpdateCommentOutput output = commentsClient.updateComment(validRequest.getCommentId(), input);

                  UpdateCommentResponse response = createResponse(output);
                  log.info("End update comment output: {}", response);
                  return response;
                })
                .toEither()
                .mapLeft(t -> Match(t).of(
                    feignCase(),
                    defaultCase(t)
                ))
        );
  }

  private UpdateCommentResponse createResponse(UpdateCommentOutput output) {
    CommentResponse commentResponse = conversionService.convert(output.getOutput(), CommentResponse.class);
    UpdateCommentResponse response = UpdateCommentResponse.builder()
        .commentResponse(commentResponse)
        .build();
    return response;
  }
}
