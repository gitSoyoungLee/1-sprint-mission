package com.spirnt.mission.discodeit.dto.readStatus;

import java.time.Instant;

public record ReadStatusUpdateRequest(
    Instant lastReadAt
) {

}
