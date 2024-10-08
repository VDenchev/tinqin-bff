package com.tinqinacademy.bff.api.apiroutes;

public class RestApiRoutes {

  public static final String ROOT = "/api/v1";

  public static final String HOTEL = ROOT + "/hotel";
  public static final String SYSTEM = ROOT + "/system";

  public static final String ROOM_ID = "/{roomId}";
  public static final String ROOM = "/room";
  public static final String REGISTER = "/register";
  public static final String BOOKING_ID = "/{bookingId}";
  public static final String COMMENT_ID = "/{commentId}";
  public static final String COMMENT = "/comment";


  public static final String CHECK_AVAILABLE_ROOMS = HOTEL + "/rooms";
  public static final String GET_ROOM = HOTEL + ROOM_ID;
  public static final String BOOK_ROOM = HOTEL + ROOM_ID;
  public static final String REMOVE_BOOKING = HOTEL + BOOKING_ID;
  public static final String GET_COMMENTS = HOTEL + ROOM_ID + COMMENT;
  public static final String ADD_COMMENT = HOTEL + ROOM_ID + COMMENT;
  public static final String UPDATE_COMMENT = HOTEL + COMMENT + COMMENT_ID;

  public static final String REGISTER_VISITORS = SYSTEM + REGISTER + BOOKING_ID;
  public static final String SEARCH_VISITORS = SYSTEM + REGISTER;
  public static final String ADD_ROOM = SYSTEM + ROOM;
  public static final String UPDATE_ROOM = SYSTEM + ROOM + ROOM_ID;
  public static final String PARTIAL_UPDATE_ROOM = SYSTEM + ROOM + ROOM_ID;
  public static final String DELETE_ROOM = SYSTEM + ROOM + ROOM_ID;
  public static final String UPDATE_COMMENT_BY_ADMIN = SYSTEM + COMMENT + COMMENT_ID;
  public static final String DELETE_COMMENT = SYSTEM + COMMENT + COMMENT_ID;
}
