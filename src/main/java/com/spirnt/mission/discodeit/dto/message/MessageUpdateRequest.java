package com.spirnt.mission.discodeit.dto.message;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(description = "수정할 Message 내용")
public record MessageUpdateRequest(
    @Size(max = 150, message = "메시지는 최대 150자까지 가능합니다.") String newContent
) {

}