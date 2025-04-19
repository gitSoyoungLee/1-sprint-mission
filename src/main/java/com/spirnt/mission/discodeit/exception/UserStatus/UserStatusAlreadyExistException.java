package com.spirnt.mission.discodeit.exception.UserStatus;

import com.spirnt.mission.discodeit.exception.ErrorCode;
import java.time.Instant;
import java.util.Map;

public class UserStatusAlreadyExistException extends UserStatusException {

  public UserStatusAlreadyExistException(Instant timestamp, Map<String, Object> details) {
    super(timestamp, ErrorCode.USERSTATUS_ALREADY_EXISTS, details);
  }
}
