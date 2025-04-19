package com.spirnt.mission.discodeit.exception.User;

import com.spirnt.mission.discodeit.exception.DiscodeitException;
import com.spirnt.mission.discodeit.exception.ErrorCode;
import java.time.Instant;
import java.util.Map;

public class UserException extends DiscodeitException {

  public UserException(Instant timestamp, ErrorCode errorCode, Map<String, Object> details) {
    super(timestamp, errorCode, details);
  }
}
