package com.tinqinacademy.bff.rest.controllers;

import com.tinqinacademy.bff.api.base.Response;
import com.tinqinacademy.bff.api.enums.BathroomType;
import com.tinqinacademy.bff.api.enums.BedType;
import com.tinqinacademy.bff.api.errors.ErrorResponse;
import com.tinqinacademy.bff.api.operations.addcomment.operation.AddCommentOperation;
import com.tinqinacademy.bff.api.operations.addcomment.request.AddCommentRequest;
import com.tinqinacademy.bff.api.operations.addcomment.response.AddCommentResponse;
import com.tinqinacademy.bff.api.operations.bookroom.operation.BookRoomOperation;
import com.tinqinacademy.bff.api.operations.bookroom.request.BookRoomRequest;
import com.tinqinacademy.bff.api.operations.bookroom.response.BookRoomResponse;
import com.tinqinacademy.bff.api.operations.checkavailablerooms.operation.CheckAvailableRoomsOperation;
import com.tinqinacademy.bff.api.operations.checkavailablerooms.request.CheckAvailableRoomsRequest;
import com.tinqinacademy.bff.api.operations.checkavailablerooms.response.CheckAvailableRoomsResponse;
import com.tinqinacademy.bff.api.operations.getcomments.operation.GetCommentsOperation;
import com.tinqinacademy.bff.api.operations.getcomments.request.GetCommentsRequest;
import com.tinqinacademy.bff.api.operations.getcomments.response.GetCommentsResponse;
import com.tinqinacademy.bff.api.operations.getroom.operation.GetRoomOperation;
import com.tinqinacademy.bff.api.operations.getroom.request.GetRoomRequest;
import com.tinqinacademy.bff.api.operations.getroom.response.GetRoomResponse;
import com.tinqinacademy.bff.api.operations.removebooking.operation.RemoveBookingOperation;
import com.tinqinacademy.bff.api.operations.removebooking.request.RemoveBookingRequest;
import com.tinqinacademy.bff.api.operations.removebooking.response.RemoveBookingResponse;
import com.tinqinacademy.bff.api.operations.updatecomment.operation.UpdateCommentOperation;
import com.tinqinacademy.bff.api.operations.updatecomment.request.UpdateCommentRequest;
import com.tinqinacademy.bff.api.operations.updatecomment.response.UpdateCommentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.tinqinacademy.bff.api.apiroutes.RestApiRoutes.BOOK_ROOM;
import static com.tinqinacademy.bff.api.apiroutes.RestApiRoutes.CHECK_AVAILABLE_ROOMS;
import static com.tinqinacademy.bff.api.apiroutes.RestApiRoutes.GET_ROOM;
import static com.tinqinacademy.bff.api.apiroutes.RestApiRoutes.REMOVE_BOOKING;
import static com.tinqinacademy.bff.api.apiroutes.RestApiRoutes.ADD_COMMENT;
import static com.tinqinacademy.bff.api.apiroutes.RestApiRoutes.GET_COMMENTS;
import static com.tinqinacademy.bff.api.apiroutes.RestApiRoutes.UPDATE_COMMENT;

@RestController
@RequiredArgsConstructor
public class HotelController extends BaseController {

  private final BookRoomOperation bookRoomOperation;
  private final CheckAvailableRoomsOperation checkAvailableRoomsOperation;
  private final GetRoomOperation getRoomOperation;
  private final RemoveBookingOperation removeBookingOperation;
  private final GetCommentsOperation getCommentsOperation;
  private final AddCommentOperation addCommentOperation;
  private final UpdateCommentOperation updateCommentOperation;

  @Operation(
      summary = "Checks if a room is available",
      description = "Checks whether a room is available fro a certain period. Room requirements should come as query parameters in URL."
  )
  @ApiResponses(value = {
      @ApiResponse(
          description = "Returns the IDs of all available rooms",
          responseCode = "200"
      ),
  })
  @GetMapping(CHECK_AVAILABLE_ROOMS)
  public ResponseEntity<Response> checkAvailableRooms(
      @RequestParam(required = false) LocalDate startDate,
      @RequestParam(required = false) LocalDate endDate,
      @RequestParam(required = false) Integer bedCount,
      @RequestParam(required = false) List<String> bedSizes,
      @RequestParam(required = false) String bathroomType
  ) {
    if (Objects.isNull(bedSizes)) {
      bedSizes = new ArrayList<>();
    }

    CheckAvailableRoomsRequest request = CheckAvailableRoomsRequest.builder()
        .startDate(startDate)
        .endDate(endDate)
        .bedCount(bedCount)
        .bedSizes(bedSizes.stream().map(BedType::getByCode).toList())
        .bathroomType(BathroomType.getByCode(bathroomType))
        .build();

    Either<? extends ErrorResponse, CheckAvailableRoomsResponse> response = checkAvailableRoomsOperation.process(request);
    return createResponse(response, HttpStatus.OK);
  }


  @Operation(
      summary = "Returns room details",
      description = "Returns basic info for a room with the specified id"
  )
  @ApiResponses(value = {
      @ApiResponse(
          description = "Room is successfully retrieved",
          responseCode = "200"
      ),
      @ApiResponse(
          description = "Room with the provided id does not exist",
          responseCode = "404"
      ),
      @ApiResponse(
          description = "Validation error",
          responseCode = "422"
      )
  })
  @GetMapping(GET_ROOM)
  public ResponseEntity<Response> getRoom(
      @PathVariable String roomId
  ) {
    GetRoomRequest request = GetRoomRequest.builder()
        .roomId(roomId)
        .build();
    Either<? extends ErrorResponse, GetRoomResponse> response = getRoomOperation.process(request);
    return createResponse(response, HttpStatus.OK);
  }


  @Operation(
      summary = "Books a hotel room",
      description = "Books the room with the corresponding id"
  )
  @ApiResponses(value = {
      @ApiResponse(
          description = "Room is successfully booked",
          responseCode = "200"
      ),
      @ApiResponse(
          description = "Validation error",
          responseCode = "422"
      ),
      @ApiResponse(
          description = "Room already booked for the specified period",
          responseCode = "409"
      ),
      @ApiResponse(
          description = "You are not authorized",
          responseCode = "401"
      )
  })
  @PostMapping(BOOK_ROOM)
  @PreAuthorize("hasRole('USER')")
  @SecurityRequirement(name = "bearerAuth")
  public ResponseEntity<Response> bookRoom(@PathVariable String roomId, @RequestBody BookRoomRequest request) {
    request.setRoomId(roomId);
    Either<? extends ErrorResponse, BookRoomResponse> response = bookRoomOperation.process(request);

    return createResponse(response, HttpStatus.OK);
  }


  @Operation(
      summary = "Unbooks a hotel room",
      description = "Unbooks the room a user had already booked"
  )
  @ApiResponses(value = {
      @ApiResponse(
          description = "Room is successfully unbooked",
          responseCode = "200"
      ),
      @ApiResponse(
          description = "No book with the provided id",
          responseCode = "404"
      ),
      @ApiResponse(
          description = "Validation error",
          responseCode = "422"
      ),
      @ApiResponse(
          description = "You are not authorized",
          responseCode = "401"
      )
  })
  @DeleteMapping(REMOVE_BOOKING)
  @PreAuthorize("hasRole('USER')")
  @SecurityRequirement(name = "bearerAuth")
  public ResponseEntity<Response> removeBooking(@PathVariable UUID bookingId) {
    RemoveBookingRequest request = RemoveBookingRequest.builder()
        .bookingId(bookingId.toString())
        .build();
    Either<? extends ErrorResponse, RemoveBookingResponse> response = removeBookingOperation.process(request);

    return createResponse(response, HttpStatus.OK);
  }


  @Operation(summary = "Retrieves all comments for the provided room")
  @ApiResponses(value = {
      @ApiResponse(
          description = "Successful retrieval of comments",
          responseCode = "200"
      ),
      @ApiResponse(
          description = "Validation error",
          responseCode = "422"
      ),
      @ApiResponse(
          description = "Invalid page number",
          responseCode = "400"
      )
  })
  @GetMapping(GET_COMMENTS)
  public ResponseEntity<Response> getComments(
      @PathVariable(name = "roomId") String roomId,
      @RequestParam(required = false) Integer pageNumber,
      @RequestParam(required = false) Integer pageSize
  ) {
    GetCommentsRequest request = GetCommentsRequest.builder()
        .roomId(roomId)
        .pageNumber(pageNumber)
        .pageSize(pageSize)
        .build();
    Either<? extends ErrorResponse, GetCommentsResponse> response = getCommentsOperation.process(request);

    return createResponse(response, HttpStatus.OK);
  }


  @Operation(summary = "Leaves a comment regarding a certain room")
  @ApiResponses(value = {
      @ApiResponse(
          description = "Successfully leaves a comment and returns its id",
          responseCode = "201"
      ),
      @ApiResponse(
          description = "Room with the provided id does not exist",
          responseCode = "404"
      ),
      @ApiResponse(
          description = "Validation error",
          responseCode = "422"
      ),
      @ApiResponse(
          description = "You are not authorized",
          responseCode = "401"
      )
  })
  @PostMapping(ADD_COMMENT)
  @PreAuthorize("hasRole('USER')")
  @SecurityRequirement(name = "bearerAuth")
  public ResponseEntity<Response> addComment(
      @PathVariable String roomId,
      @RequestBody AddCommentRequest request
  ) {
    request.setRoomId(roomId);
    Either<? extends ErrorResponse, AddCommentResponse> response = addCommentOperation.process(request);
    return createResponse(response, HttpStatus.CREATED);
  }


  @Operation(summary = "User can edit his comment")
  @ApiResponses(value = {
      @ApiResponse(
          description = "Editing the comment was successful",
          responseCode = "200"
      ),
      @ApiResponse(
          description = "Comment with provided id does not exist",
          responseCode = "404"
      ),
      @ApiResponse(
          description = "Validation error",
          responseCode = "422"
      ),
      @ApiResponse(
          description = "You are not authorized",
          responseCode = "401"
      )
  })
  @PatchMapping(UPDATE_COMMENT)
  @PreAuthorize("hasRole('USER')")
  @SecurityRequirement(name = "bearerAuth")
  public ResponseEntity<Response> updateComment(
      @PathVariable String commentId,
      @RequestBody UpdateCommentRequest request
  ) {
    request.setCommentId(commentId);
    Either<? extends ErrorResponse, UpdateCommentResponse> response = updateCommentOperation.process(request);

    return createResponse(response, HttpStatus.OK);
  }
}
