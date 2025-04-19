package com.spirnt.mission.discodeit.exception.Channel;

import com.spirnt.mission.discodeit.dto.ErrorResponse;
import java.time.Instant;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(1)
public class ChannelExceptionHandler {

  @ExceptionHandler(ChannelNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleChannelNotFoundException(ChannelNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(
            new ErrorResponse(Instant.now(), e.getErrorCode().getCode(),
                e.getErrorCode().getMessage(),
                e.getDetails(), e.getClass().getSimpleName(), 404));
  }

  @ExceptionHandler(PrivateChannelUpdateException.class)
  public ResponseEntity<ErrorResponse> handlePrivateChannelUpdateException(
      PrivateChannelUpdateException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            new ErrorResponse(Instant.now(), e.getErrorCode().getCode(),
                e.getErrorCode().getMessage(),
                e.getDetails(), e.getClass().getSimpleName(), 400));
  }
}
