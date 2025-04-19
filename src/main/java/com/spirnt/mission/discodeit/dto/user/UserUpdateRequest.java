package com.spirnt.mission.discodeit.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;


@Schema(description = "수정할 User 정보")
public record UserUpdateRequest(
    @Size(min = 1, max = 20, message = "이름은 1~20자 사이여야 합니다.")
    String newUsername,
    @Email
    String newEmail,
    @Size(min = 5, max = 20, message = "비밀번호는 5~20자 사이여야 합니다.")
    String newPassword
) {

}
