package com.spirnt.mission.discodeit.dto.channel;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Schema(description = "Private Channel 생성 정보")
public record PrivateChannelCreateRequest(
    @NotNull @Size(min = 2) List<UUID> participantIds
) {

}
