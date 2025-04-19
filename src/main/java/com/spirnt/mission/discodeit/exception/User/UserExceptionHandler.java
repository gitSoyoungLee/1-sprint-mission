package com.spirnt.mission.discodeit.exception.User;

import com.spirnt.mission.discodeit.dto.ErrorResponse;
import java.time.Instant;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(1)
public class UserExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(
            new ErrorResponse(Instant.now(), e.getErrorCode().getCode(),
                e.getErrorCode().getMessage(),
                e.getDetails(), e.getClass().getSimpleName(), 404));
  }

  @ExceptionHandler(UserAlreadyExistException.class)
  public ResponseEntity<ErrorResponse> handleUserAlreadyExistException(
      UserAlreadyExistException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            new ErrorResponse(Instant.now(), e.getErrorCode().getCode(),
                e.getErrorCode().getMessage(),
                e.getDetails(), e.getClass().getSimpleName(), 400));
  }

  @ExceptionHandler(InvalidPasswordException.class)
  public ResponseEntity<ErrorResponse> handleInvalidPasswordException(InvalidPasswordException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            new ErrorResponse(Instant.now(), e.getErrorCode().getCode(),
                e.getErrorCode().getMessage(),
                e.getDetails(), e.getClass().getSimpleName(), 400));
  }
}
