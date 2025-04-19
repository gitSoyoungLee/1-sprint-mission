package com.spirnt.mission.discodeit.exception.UserStatus;

import com.spirnt.mission.discodeit.dto.ErrorResponse;
import java.time.Instant;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(1)
public class UserStatusExceptionHandler {

  @ExceptionHandler(UserStatusNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUserStatusNotFoundException(
      UserStatusNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(
            new ErrorResponse(Instant.now(), e.getErrorCode().getCode(),
                e.getErrorCode().getMessage(),
                e.getDetails(), e.getClass().getSimpleName(), 404));
  }

  @ExceptionHandler(UserStatusAlreadyExistException.class)
  public ResponseEntity<ErrorResponse> handleUserStatusAlreadyExistsException(
      UserStatusAlreadyExistException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            new ErrorResponse(Instant.now(), e.getErrorCode().getCode(),
                e.getErrorCode().getMessage(),
                e.getDetails(), e.getClass().getSimpleName(), 400));
  }
}
