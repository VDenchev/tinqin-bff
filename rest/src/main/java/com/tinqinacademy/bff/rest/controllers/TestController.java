package com.tinqinacademy.bff.rest.controllers;

import com.tinqinacademy.comments.api.operations.addcomment.input.AddCommentInput;
import com.tinqinacademy.comments.restexport.client.CommentsClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
  private final CommentsClient commentsClient;


  @PostMapping("/comments/{roomId}")
  public ResponseEntity<?> addComment(
      @PathVariable(name = "roomId") String roomId,
      @RequestBody AddCommentInput addCommentInput
      ) {
      return new ResponseEntity<>(commentsClient.addComment(roomId, addCommentInput), HttpStatus.OK);
  }

  @GetMapping("/comments/{roomId}")
  public ResponseEntity<?> getComments(
      @PathVariable(name = "roomId") String roomId,
      @RequestParam(required = false) Integer pageNumber,
      @RequestParam(required = false) Integer pageSize
  ) {
    return new ResponseEntity<>(commentsClient.getComments(roomId, pageNumber, pageSize), HttpStatus.OK);
  }
}
