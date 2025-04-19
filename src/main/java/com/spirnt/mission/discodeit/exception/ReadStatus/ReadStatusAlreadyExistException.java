package com.spirnt.mission.discodeit.exception.ReadStatus;

import com.spirnt.mission.discodeit.exception.ErrorCode;
import java.time.Instant;
import java.util.Map;

public class ReadStatusAlreadyExistException extends ReadStatusException {

  public ReadStatusAlreadyExistException(Instant timestamp, Map<String, Object> details) {
    super(timestamp, ErrorCode.READSTATUS_ALREADY_EXISTS, details);
  }
}
