package com.spirnt.mission.discodeit.dto.channel;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(description = "수정할 Channel 정보")
public record PublicChannelUpdateRequest(
    @Size(min = 1, max = 20, message = "이름은 1~20자 사이여야 합니다.")
    String newName,
    @Size(max = 20, message = "설명은 최대 20자까지 가능합니다.")
    String newDescription
) {


}
