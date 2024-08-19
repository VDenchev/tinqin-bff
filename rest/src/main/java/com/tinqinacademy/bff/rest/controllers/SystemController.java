package com.tinqinacademy.bff.rest.controllers;

import com.tinqinacademy.bff.api.base.Response;
import com.tinqinacademy.bff.api.errors.ErrorResponse;
import com.tinqinacademy.bff.api.models.request.VisitorDetailsRequest;
import com.tinqinacademy.bff.api.operations.addroom.operation.AddRoomOperation;
import com.tinqinacademy.bff.api.operations.addroom.request.AddRoomRequest;
import com.tinqinacademy.bff.api.operations.addroom.response.AddRoomResponse;
import com.tinqinacademy.bff.api.operations.deletecomment.operation.DeleteCommentOperation;
import com.tinqinacademy.bff.api.operations.deletecomment.request.DeleteCommentRequest;
import com.tinqinacademy.bff.api.operations.deletecomment.response.DeleteCommentResponse;
import com.tinqinacademy.bff.api.operations.deleteroom.operation.DeleteRoomOperation;
import com.tinqinacademy.bff.api.operations.deleteroom.request.DeleteRoomRequest;
import com.tinqinacademy.bff.api.operations.deleteroom.response.DeleteRoomResponse;
import com.tinqinacademy.bff.api.operations.partialupdateroom.operation.PartialUpdateRoomOperation;
import com.tinqinacademy.bff.api.operations.partialupdateroom.request.PartialUpdateRoomRequest;
import com.tinqinacademy.bff.api.operations.partialupdateroom.response.PartialUpdateRoomResponse;
import com.tinqinacademy.bff.api.operations.registervisitors.operation.RegisterVisitorsOperation;
import com.tinqinacademy.bff.api.operations.registervisitors.request.RegisterVisitorsRequest;
import com.tinqinacademy.bff.api.operations.registervisitors.response.RegisterVisitorsResponse;
import com.tinqinacademy.bff.api.operations.searchvisitors.operation.SearchVisitorsOperation;
import com.tinqinacademy.bff.api.operations.searchvisitors.request.SearchVisitorsRequest;
import com.tinqinacademy.bff.api.operations.searchvisitors.response.SearchVisitorsResponse;
import com.tinqinacademy.bff.api.operations.updatecommentbyadmin.operation.UpdateCommentByAdminOperation;
import com.tinqinacademy.bff.api.operations.updatecommentbyadmin.request.UpdateCommentByAdminRequest;
import com.tinqinacademy.bff.api.operations.updatecommentbyadmin.response.UpdateCommentByAdminResponse;
import com.tinqinacademy.bff.api.operations.updateroom.operation.UpdateRoomOperation;
import com.tinqinacademy.bff.api.operations.updateroom.request.UpdateRoomRequest;
import com.tinqinacademy.bff.api.operations.updateroom.response.UpdateRoomResponse;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import static com.tinqinacademy.bff.api.apiroutes.RestApiRoutes.ADD_ROOM;
import static com.tinqinacademy.bff.api.apiroutes.RestApiRoutes.DELETE_ROOM;
import static com.tinqinacademy.bff.api.apiroutes.RestApiRoutes.PARTIAL_UPDATE_ROOM;
import static com.tinqinacademy.bff.api.apiroutes.RestApiRoutes.REGISTER_VISITORS;
import static com.tinqinacademy.bff.api.apiroutes.RestApiRoutes.SEARCH_VISITORS;
import static com.tinqinacademy.bff.api.apiroutes.RestApiRoutes.UPDATE_ROOM;
import static com.tinqinacademy.bff.api.apiroutes.RestApiRoutes.UPDATE_COMMENT_BY_ADMIN;
import static com.tinqinacademy.bff.api.apiroutes.RestApiRoutes.DELETE_COMMENT;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class SystemController extends BaseController {

  private final RegisterVisitorsOperation registerVisitorsOperation;
  private final SearchVisitorsOperation searchVisitorsOperation;
  private final AddRoomOperation addRoomOperation;
  private final UpdateRoomOperation updateRoomOperation;
  private final PartialUpdateRoomOperation partialUpdateRoomOperation;
  private final DeleteRoomOperation deleteRoomOperation;
  private final UpdateCommentByAdminOperation updateCommentByAdminOperation;
  private final DeleteCommentOperation deleteCommentOperation;

  @Operation(
      summary = "Registers a visitor as a room renter"
  )
  @ApiResponses(value = {
      @ApiResponse(
          description = "Successful visitor registration",
          responseCode = "200"
      ),
      @ApiResponse(
          description = "Validation error",
          responseCode = "422"
      ),
      @ApiResponse(
          description = "Booking not found",
          responseCode = "404"
      ),
      @ApiResponse(
          description = "Exceeds room capacity",
          responseCode = "400"
      ),
      @ApiResponse(
          description = "Visitor already exists in booking",
          responseCode = "409"
      ),
      @ApiResponse(
          description = "You are not authorized",
          responseCode = "401"
      ),
      @ApiResponse(
          description = "You don't have permission",
          responseCode = "403"
      )
  })
  @PostMapping(REGISTER_VISITORS)
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Response> registerVisitors(
      @RequestBody RegisterVisitorsRequest request,
      @PathVariable String bookingId
  ) {
    request.setBookingId(bookingId);
    Either<? extends ErrorResponse, RegisterVisitorsResponse> response = registerVisitorsOperation.process(request);

    return createResponse(response, HttpStatus.OK);
  }


  @Operation(
      summary = "A report which returns when a room was occupied and by who"
  )
  @ApiResponses(value = {
      @ApiResponse(
          description = "A list of all rooms and when they were visited",
          responseCode = "200"
      ),
      @ApiResponse(
          description = "You are not authorized",
          responseCode = "401"
      ),
      @ApiResponse(
          description = "You don't have permission",
          responseCode = "403"
      )
  })
  @GetMapping(SEARCH_VISITORS)
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Response> searchVisitors(
      @RequestParam(required = false) LocalDate startDate,
      @RequestParam(required = false) LocalDate endDate,
      @RequestParam(required = false) String firstName,
      @RequestParam(required = false) String lastName,
      @RequestParam(required = false) LocalDate birthDate,
      @RequestParam(required = false) String phoneNo,
      @RequestParam(required = false) LocalDate idCardValidity,
      @RequestParam(required = false) String idCardIssueAuthority,
      @RequestParam(required = false) LocalDate idCardIssueDate,
      @RequestParam(required = false) String idCardNo,
      @RequestParam(required = false) String roomNo
  ) {
    VisitorDetailsRequest details = VisitorDetailsRequest.builder()
        .startDate(startDate)
        .endDate(endDate)
        .firstName(firstName)
        .lastName(lastName)
        .birthDate(birthDate)
        .phoneNo(phoneNo)
        .idCardValidity(idCardValidity)
        .idCardIssueAuthority(idCardIssueAuthority)
        .idCardIssueDate(idCardIssueDate)
        .idCardNo(idCardNo)
        .build();
    SearchVisitorsRequest request = SearchVisitorsRequest.builder()
        .visitorDetailsRequest(details)
        .roomNo(roomNo)
        .build();

    Either<? extends ErrorResponse, SearchVisitorsResponse> response = searchVisitorsOperation.process(request);

    return createResponse(response, HttpStatus.OK);
  }


  @Operation(
      summary = "Creates a room"
  )
  @ApiResponses(value = {
      @ApiResponse(
          description = "The id of the newly created room",
          responseCode = "201"
      ),
      @ApiResponse(
          description = "Validation error",
          responseCode = "422"
      ),
      @ApiResponse(
          description = "Room already exists",
          responseCode = "409"
      ),
      @ApiResponse(
          description = "You are not authorized",
          responseCode = "401"
      ),
      @ApiResponse(
          description = "You don't have permission",
          responseCode = "403"
      ),
  })
  @PostMapping(ADD_ROOM)
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Response> addRoom(@RequestBody AddRoomRequest request) {
    Either<? extends ErrorResponse, AddRoomResponse> response = addRoomOperation.process(request);

    return createResponse(response, HttpStatus.CREATED);
  }


  @Operation(
      summary = "Updates room details"
  )
  @ApiResponses(value = {
      @ApiResponse(
          description = "Returns the id of the updated room",
          responseCode = "200"
      ),
      @ApiResponse(
          description = "Validation error",
          responseCode = "422"
      ),
      @ApiResponse(
          description = "Room with the provided id doesn't exist",
          responseCode = "404"
      ),
      @ApiResponse(
          description = "Room already exists",
          responseCode = "409"
      ),
      @ApiResponse(
          description = "You are not authorized",
          responseCode = "401"
      ),
      @ApiResponse(
          description = "You don't have permission",
          responseCode = "403"
      ),
  })
  @PutMapping(UPDATE_ROOM)
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Response> updateRoom(
      @PathVariable String roomId,
      @RequestBody UpdateRoomRequest request
  ) {

    request.setRoomId(roomId);
    Either<? extends ErrorResponse, UpdateRoomResponse> response = updateRoomOperation.process(request);

    return createResponse(response, HttpStatus.OK);
  }


  @Operation(
      summary = "Partially updates room details"
  )
  @ApiResponses(value = {
      @ApiResponse(
          description = "Returns the id of the updated room",
          responseCode = "200"
      ),
      @ApiResponse(
          description = "Validation error",
          responseCode = "422"
      ),
      @ApiResponse(
          description = "Room with the provided id doesn't exist",
          responseCode = "409"
      ),
      @ApiResponse(
          description = "Room already exists",
          responseCode = "404"
      ),
      @ApiResponse(
          description = "You are not authorized",
          responseCode = "401"
      ),
      @ApiResponse(
          description = "You don't have permission",
          responseCode = "403"
      ),
  })
  @PatchMapping(PARTIAL_UPDATE_ROOM)
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Response> partialUpdateRoom(
      @PathVariable String roomId,
      @RequestBody PartialUpdateRoomRequest request
  ) {
    request.setRoomId(roomId);
    Either<? extends ErrorResponse, PartialUpdateRoomResponse> response = partialUpdateRoomOperation.process(request);

    return createResponse(response, HttpStatus.OK);
  }


  @Operation(
      summary = "Deletes a room with provided id"
  )
  @ApiResponses(value = {
      @ApiResponse(
          description = "An empty response indicating that the deletion was successful",
          responseCode = "200"
      ),
      @ApiResponse(
          description = "Validation error",
          responseCode = "422"
      ),
      @ApiResponse(
          description = "Room with the provided id doesn't exist",
          responseCode = "404"
      ),
      @ApiResponse(
          description = "You are not authorized",
          responseCode = "401"
      ),
      @ApiResponse(
          description = "You don't have permission",
          responseCode = "403"
      ),
  })
  @DeleteMapping(DELETE_ROOM)
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Response> deleteRoom(@PathVariable("roomId") String roomId) {
    DeleteRoomRequest request = DeleteRoomRequest.builder()
        .id(roomId)
        .build();

    Either<? extends ErrorResponse, DeleteRoomResponse> response = deleteRoomOperation.process(request);

    return createResponse(response, HttpStatus.OK);
  }


  @Operation(summary = "Admin can edit any comment for a certain room")
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
      ),
      @ApiResponse(
          description = "You don't have permission",
          responseCode = "403"
      )
  })
  @PutMapping(UPDATE_COMMENT_BY_ADMIN)
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Response> updateCommentByAdmin(
      @PathVariable String commentId,
      @RequestBody UpdateCommentByAdminRequest request
  ) {
    request.setCommentId(commentId);
    Either<? extends ErrorResponse, UpdateCommentByAdminResponse> response = updateCommentByAdminOperation.process(request);

    return createResponse(response, HttpStatus.OK);
  }


  @Operation(summary = "Deletes a comment with the provided id")
  @ApiResponses(value = {
      @ApiResponse(
          description = "Successfully deleted comment",
          responseCode = "200"
      ),
      @ApiResponse(
          description = "Comment with provided id does not exist",
          responseCode = "404"
      ),
      @ApiResponse(
          description = "You are not authorized",
          responseCode = "401"
      ),
      @ApiResponse(
          description = "You don't have permission",
          responseCode = "403"
      ),
  })
  @DeleteMapping(DELETE_COMMENT)
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Response> deleteComment(
      @PathVariable String commentId
  ) {
    DeleteCommentRequest request = DeleteCommentRequest.builder()
        .commentId(commentId)
        .build();
    Either<? extends ErrorResponse, DeleteCommentResponse> output = deleteCommentOperation.process(request);

    return createResponse(output, HttpStatus.OK);
  }
}
