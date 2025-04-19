package com.spirnt.mission.discodeit.exception.UserStatus;

import com.spirnt.mission.discodeit.exception.DiscodeitException;
import com.spirnt.mission.discodeit.exception.ErrorCode;
import java.time.Instant;
import java.util.Map;

public class UserStatusException extends DiscodeitException {

  public UserStatusException(Instant timestamp, ErrorCode errorCode, Map<String, Object> details) {
    super(timestamp, errorCode, details);
  }
}
