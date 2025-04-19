package com.spirnt.mission.discodeit.service.basic;

import com.spirnt.mission.discodeit.dto.userStatus.UserStatusCreateRequest;
import com.spirnt.mission.discodeit.dto.userStatus.UserStatusDto;
import com.spirnt.mission.discodeit.dto.userStatus.UserStatusUpdateRequest;
import com.spirnt.mission.discodeit.entity.User;
import com.spirnt.mission.discodeit.entity.UserStatus;
import com.spirnt.mission.discodeit.exception.Message.MessageNotFoundException;
import com.spirnt.mission.discodeit.exception.User.UserNotFoundException;
import com.spirnt.mission.discodeit.exception.UserStatus.UserStatusAlreadyExistException;
import com.spirnt.mission.discodeit.exception.UserStatus.UserStatusNotFoundException;
import com.spirnt.mission.discodeit.mapper.UserStatusMapper;
import com.spirnt.mission.discodeit.repository.UserRepository;
import com.spirnt.mission.discodeit.repository.UserStatusRepository;
import com.spirnt.mission.discodeit.service.UserStatusService;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BasicUserStatusService implements UserStatusService {

  private final UserStatusMapper userStatusMapper;

  private final UserStatusRepository userStatusRepository;
  private final UserRepository userRepository;

  @Override
  public UserStatusDto create(UserStatusCreateRequest userStatusCreateRequest) {
    UUID userId = userStatusCreateRequest.userId();
    // User가 존재하지 않으면 예외 발생
    User user = userRepository.findById(userId)
        .orElseThrow(() -> {
          log.warn("[Creating UserStatus Failed: User with id {} not found]", userId);
          return new UserNotFoundException(Instant.now(), Map.of("userId", userId));
        });
    // 이미 User와 관련된 객체가 존재하면 예외 발생
    if (userStatusRepository.existsByUserId(userId)) {
      log.warn(
          "[Creating UserStatus Failed: UserStatus with userId {} already exists]",
          userId);
      throw new UserStatusAlreadyExistException(Instant.now(),
          Map.of("userID", userId));
    }
    UserStatus userStatus = userStatusRepository.save(new UserStatus(user,
        userStatusCreateRequest.type(), Instant.now()));
    return userStatusMapper.toDto(userStatus);
  }

  @Override
  public UserStatusDto find(UUID userStatusId) {
    UserStatus userStatus = userStatusRepository.findById(userStatusId)
        .orElseThrow(() -> new UserStatusNotFoundException(Instant.now(),
            Map.of("userStatusId", userStatusId)));
    return userStatusMapper.toDto(userStatus);
  }

  @Override
  public List<UserStatusDto> findAll() {
    return userStatusRepository.findAll().stream()
        .map(userStatusMapper::toDto)
        .toList();
  }

  @Override
  public UserStatusDto update(UUID userStatusId, UserStatusUpdateRequest userStatusUpdateRequest,
      Instant serverTime) {
    UserStatus userStatus = userStatusRepository.findById(userStatusId)
        .orElseThrow(() -> {
          log.warn("[Updating UserStatus Failed: UserStatus with id {} not found]",
              userStatusId);
          return new UserStatusNotFoundException(Instant.now(),
              Map.of("userStatusId", userStatusId));
        });
    userStatus.update(userStatusUpdateRequest.newLastActiveAt());
    return userStatusMapper.toDto(userStatus);
  }

  @Override
  public UserStatusDto updateByUserId(UUID userId,
      UserStatusUpdateRequest userStatusUpdateRequest) {
    // User가 존재하지 않으면 예외 발생
    if (!userRepository.existsById(userId)) {
      log.warn("[Updating UserStatus Failed: User with id {} not found]", userId);
      throw new UserNotFoundException(Instant.now(), Map.of("userId", userId));
    }
    UserStatus userStatus = userStatusRepository.findByUserId(userId)
        .orElseThrow(() -> {
          log.warn("[Updating UserStatus Failed: UserStatus with userId {} not found]",
              userId);
          return new UserStatusNotFoundException(Instant.now(),
              Map.of("userId", userId));
        });
    userStatus.update(userStatusUpdateRequest.newLastActiveAt());
    return userStatusMapper.toDto(userStatus);
  }

  @Override
  public void delete(UUID userStatusId) {
    UserStatus userStatus = userStatusRepository.findById(userStatusId)
        .orElseThrow(() -> {
          log.warn("[Deleting UserStatus Failed: UserStatus with id {} not found]", userStatusId);
          return new MessageNotFoundException(Instant.now(), Map.of("userStatusId", userStatusId));
        });
    userStatusRepository.delete(userStatus);
  }

}
