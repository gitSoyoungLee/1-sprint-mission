package com.spirnt.mission.discodeit.dto.userStatus;

import com.spirnt.mission.discodeit.entity.UserStatusType;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

public record UserStatusCreateRequest(
    @NotNull UUID userId,
    @NotNull UserStatusType type,
    @NotNull Instant lastSeenAt
) {

}
