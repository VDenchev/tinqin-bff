package com.tinqinacademy.bff.rest.controllers;

import com.tinqinacademy.comments.api.operations.addcomment.input.AddCommentInput;
import com.tinqinacademy.comments.api.operations.deletecomment.output.DeleteCommentOutput;
import com.tinqinacademy.comments.restexport.client.CommentsClient;
import com.tinqinacademy.hotel.api.operations.checkavailablerooms.output.AvailableRoomsOutput;
import com.tinqinacademy.hotel.api.operations.updateroom.input.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.updateroom.output.UpdateRoomOutput;
import com.tinqinacademy.hotel.restexport.client.HotelClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {
  private final CommentsClient commentsClient;
  private final HotelClient hotelClient;


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

  @DeleteMapping("/comments/{commentId}")
  public ResponseEntity<?> deleteComment(@PathVariable String commentId) {
    DeleteCommentOutput output = commentsClient.deleteComment(commentId);
    return new ResponseEntity<>(output, HttpStatus.OK);
  }

  @PutMapping("/rooms/{roomId}")
  public ResponseEntity<?> updateRoom(@PathVariable String roomId, @RequestBody UpdateRoomInput input) {
    UpdateRoomOutput output = hotelClient.updateRoom(roomId, input);
    return new ResponseEntity<>(output, HttpStatus.OK);
  }

  @GetMapping("/room")
  public ResponseEntity<?> checkRoomAvailability(
      @RequestParam(required = false) LocalDate startDate,
      @RequestParam(required = false) LocalDate endDate,
      @RequestParam(required = false) Integer bedCount,
      @RequestParam(required = false) List<String> bedSizes,
      @RequestParam(required = false) String bathroomType
  ) {
    AvailableRoomsOutput output = hotelClient.checkAvailableRooms(startDate, endDate, bedCount, bedSizes, bathroomType);
    return new ResponseEntity<>(output, HttpStatus.OK);
  }
}
