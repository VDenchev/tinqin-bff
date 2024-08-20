package com.tinqinacademy.bff.core.processors;

import com.tinqinacademy.bff.api.errors.ErrorResponse;
import com.tinqinacademy.bff.api.operations.deletecomment.operation.DeleteCommentOperation;
import com.tinqinacademy.bff.api.operations.deletecomment.request.DeleteCommentRequest;
import com.tinqinacademy.bff.api.operations.deletecomment.response.DeleteCommentResponse;
import com.tinqinacademy.comments.api.operations.deletecomment.output.DeleteCommentOutput;
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
public class DeleteCommentOperationProcessor extends BaseOperationProcessor implements DeleteCommentOperation {

  private final CommentsClient commentsClient;

  public DeleteCommentOperationProcessor(
      ConversionService conversionService, Validator validator,
      CommentsClient commentsClient
  ) {
    super(conversionService, validator);
    this.commentsClient = commentsClient;
  }

  @Override
  public Either<? extends ErrorResponse, DeleteCommentResponse> process(DeleteCommentRequest request) {
    return validateInput(request)
        .flatMap(validRequest ->
            Try.of(() -> {
                  log.info("Start delete comment input: {}", validRequest);

                  DeleteCommentOutput output = commentsClient.deleteComment(validRequest.getCommentId());

                  DeleteCommentResponse response = createResponse(output);
                  log.info("End delete comment output: {}", response);
                  return response;
                })
                .toEither()
                .mapLeft(t -> Match(t).of(
                    feignCase(),
                    defaultCase(t)
                ))
        );
  }

  private DeleteCommentResponse createResponse(DeleteCommentOutput deleteCommentOutput) {
    return DeleteCommentResponse.builder()
        .build();
  }
}
