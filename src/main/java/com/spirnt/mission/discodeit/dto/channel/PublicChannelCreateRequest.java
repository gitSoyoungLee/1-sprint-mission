package com.spirnt.mission.discodeit.dto.channel;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Public Channel 생성 정보")
public record PublicChannelCreateRequest(
    @NotNull(message = "이름은 null이어서는 안 됩니다.")
    @Size(min = 1, max = 20, message = "이름은 1~20자 사이여야 합니다.")
    String name,
    @Size(max = 20, message = "설명은 최대 20자까지 가능합니다.")
    String description
) {

}
