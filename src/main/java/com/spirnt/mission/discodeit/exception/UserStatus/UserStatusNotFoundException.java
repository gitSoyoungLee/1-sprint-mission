package com.spirnt.mission.discodeit.exception.UserStatus;

import com.spirnt.mission.discodeit.exception.ErrorCode;
import java.time.Instant;
import java.util.Map;

/**
 * ID로 조회하였으나 UserStatus가 존재하지 않는 경우 발생하는 예외
 */
public class UserStatusNotFoundException extends UserStatusException {

  public UserStatusNotFoundException(Instant timestamp, Map<String, Object> details) {
    super(timestamp, ErrorCode.USERSTATUS_NOT_FOUND, details);
  }
}
