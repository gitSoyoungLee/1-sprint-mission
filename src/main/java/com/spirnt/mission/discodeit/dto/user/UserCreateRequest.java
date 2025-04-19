package com.spirnt.mission.discodeit.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "User 생성 정보")
public record UserCreateRequest(
    @NotNull(message = "이름은 null이어서는 안 됩니다.")
    @Size(min = 1, max = 20, message = "이름은 1~20자 사이여야 합니다.")
    String username,
    @NotNull(message = "이메일 null이어서는 안 됩니다.")
    @Email
    String email,
    @NotNull(message = "비밀번호는 null이어서는 안 됩니다.")
    @Size(min = 5, max = 20, message = "비밀번호는 5~20자 사이여야 합니다.")
    String password
) {

}
