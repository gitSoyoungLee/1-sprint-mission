package com.spirnt.mission.discodeit.exception.BinaryContent;

import com.spirnt.mission.discodeit.dto.ErrorResponse;
import java.time.Instant;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(1)
public class BinaryContentExceptionHandler {

  @ExceptionHandler(BinaryContentNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleBinaryContentNotFoundException(
      BinaryContentNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(
            new ErrorResponse(Instant.now(), e.getErrorCode().getCode(),
                e.getErrorCode().getMessage(),
                e.getDetails(), e.getClass().getSimpleName(), 404));
  }

  @ExceptionHandler(FileException.class)
  public ResponseEntity<ErrorResponse> handleFileException(
      FileException e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(
            new ErrorResponse(Instant.now(), e.getErrorCode().getCode(),
                e.getErrorCode().getMessage(),
                e.getDetails(), e.getClass().getSimpleName(), 500));
  }
}
