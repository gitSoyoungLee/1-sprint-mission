package com.spirnt.mission.discodeit.exception;

import java.time.Instant;
import java.util.Map;

public class DiscodeitException extends RuntimeException {

  private final Instant timestamp;
  private final ErrorCode errorCode;
  private final Map<String, Object> details;

  public DiscodeitException(Instant timestamp, ErrorCode errorCode, Map<String, Object> details) {
    this.timestamp = timestamp;
    this.errorCode = errorCode;
    this.details = details;
  }

}
