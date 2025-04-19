package com.spirnt.mission.discodeit.dto.message;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

@Schema(description = "Message 생성 정보")
public record MessageCreateRequest(

    @Size(max = 150, message = "메시지는 최대 150자까지 가능합니다.") String content,
    @NotNull UUID authorId,
    @NotNull UUID channelId
) {

}
