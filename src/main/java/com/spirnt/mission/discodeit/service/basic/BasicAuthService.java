package com.spirnt.mission.discodeit.service.basic;

import com.spirnt.mission.discodeit.dto.auth.LoginRequest;
import com.spirnt.mission.discodeit.dto.user.UserDto;
import com.spirnt.mission.discodeit.dto.userStatus.UserStatusUpdateRequest;
import com.spirnt.mission.discodeit.entity.User;
import com.spirnt.mission.discodeit.exception.User.InvalidPasswordException;
import com.spirnt.mission.discodeit.exception.User.UserNotFoundException;
import com.spirnt.mission.discodeit.mapper.UserMapper;
import com.spirnt.mission.discodeit.repository.UserRepository;
import com.spirnt.mission.discodeit.service.AuthService;
import com.spirnt.mission.discodeit.service.UserStatusService;
import java.time.Instant;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BasicAuthService implements AuthService {

  private final UserMapper userMapper;
  private final UserRepository userRepository;

  private final UserStatusService userStatusService;

  @Override
  public UserDto login(LoginRequest loginRequest) {
    User user = userRepository.findByUsername(loginRequest.username())
        .orElseThrow(() -> {
          log.warn("[Login Failed: User with username {} not found]",
              loginRequest.username());
          return new UserNotFoundException(Instant.now(),
              Map.of("username", loginRequest.username()));
        });
    if (!user.getPassword().equals(loginRequest.password())) {
      log.warn("[Login Failed: Invalid Password(username: {})]", loginRequest.username());
      throw new InvalidPasswordException(Instant.now(), Map.of("userId", user.getId()));
    }
    // 로그인 성공 시 온라인 상태로 업데이트
    userStatusService.updateByUserId(user.getId(), new UserStatusUpdateRequest(Instant.now()));
    return userMapper.toDto(user);
  }
}
