package com.spirnt.mission.discodeit.exception.ReadStatus;

import com.spirnt.mission.discodeit.dto.ErrorResponse;
import java.time.Instant;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(1)
public class ReadStatusExceptionHandler {

  @ExceptionHandler(ReadStatusNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleReadStatusNotFoundException(
      ReadStatusNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(
            new ErrorResponse(Instant.now(), e.getErrorCode().getCode(),
                e.getErrorCode().getMessage(),
                e.getDetails(), e.getClass().getSimpleName(), 404));
  }

  @ExceptionHandler(ReadStatusAlreadyExistException.class)
  public ResponseEntity<ErrorResponse> handleReadStatusAlreadyExistException(
      ReadStatusAlreadyExistException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            new ErrorResponse(Instant.now(), e.getErrorCode().getCode(),
                e.getErrorCode().getMessage(),
                e.getDetails(), e.getClass().getSimpleName(), 400));
  }

}
