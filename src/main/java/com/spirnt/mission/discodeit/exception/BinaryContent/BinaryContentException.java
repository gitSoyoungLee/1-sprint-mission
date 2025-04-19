package com.spirnt.mission.discodeit.exception.BinaryContent;

import com.spirnt.mission.discodeit.exception.DiscodeitException;
import com.spirnt.mission.discodeit.exception.ErrorCode;
import java.time.Instant;
import java.util.Map;

public class BinaryContentException extends DiscodeitException {

  public BinaryContentException(Instant timestamp, ErrorCode errorCode,
      Map<String, Object> details) {
    super(timestamp, errorCode, details);
  }
}
