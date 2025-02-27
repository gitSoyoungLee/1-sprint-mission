package com.spirnt.mission.discodeit.service.basic;

import com.spirnt.mission.discodeit.dto.binaryContent.BinaryContentCreate;
import com.spirnt.mission.discodeit.dto.message.MessageCreateRequest;
import com.spirnt.mission.discodeit.dto.message.MessageUpdateRequest;
import com.spirnt.mission.discodeit.dto.userStatus.UserStatusUpdateRequest;
import com.spirnt.mission.discodeit.enity.BinaryContent;
import com.spirnt.mission.discodeit.enity.Channel;
import com.spirnt.mission.discodeit.enity.ChannelType;
import com.spirnt.mission.discodeit.enity.Message;
import com.spirnt.mission.discodeit.enity.UserStatusType;
import com.spirnt.mission.discodeit.repository.ChannelRepository;
import com.spirnt.mission.discodeit.repository.MessageRepository;
import com.spirnt.mission.discodeit.repository.UserRepository;
import com.spirnt.mission.discodeit.service.BinaryContentService;
import com.spirnt.mission.discodeit.service.MessageService;
import com.spirnt.mission.discodeit.service.ReadStatusService;
import com.spirnt.mission.discodeit.service.UserStatusService;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class BasicMessageService implements MessageService {

  private final MessageRepository messageRepository;
  private final UserRepository userRepository;
  private final ChannelRepository channelRepository;

  private final BinaryContentService binaryContentService;
  private final UserStatusService userStatusService;
  private final ReadStatusService readStatusService;

  @Override
  public Message create(MessageCreateRequest messageCreateRequest,
      List<MultipartFile> attachments) {
    // User와 Channel이 존재하는지 검증
    UUID userId = messageCreateRequest.getUserId();
    UUID channelId = messageCreateRequest.getChannelId();
    Channel channel = channelRepository.findById(channelId)
        .orElseThrow(() -> new NoSuchElementException(
            "Channel id " + messageCreateRequest.getChannelId() + " does not exist"));
    if (!userRepository.existsById(userId)) {
      throw new NoSuchElementException(
          "User id " + messageCreateRequest.getUserId() + " does not exist");
    }
    // PRIVATE 채널인데 입장하지 않은 유저가 메시지를 쓰려고 하는 경우
    if (channel.getType().equals(ChannelType.PRIVATE)
        && !readStatusService.existsByUserIdChannelId(userId, channelId)) {
      throw new IllegalStateException("User did not joined this private channel");
    }
    // 첨부 파일 업로드
    List<UUID> attachedFilesId = new ArrayList<>();
    for (MultipartFile file : Optional.ofNullable(attachments)
        .orElse(Collections.emptyList())) { // null 방지 optional 사용
      BinaryContentCreate binaryContentCreate = new BinaryContentCreate(file);
      BinaryContent binaryContent = binaryContentService.create(binaryContentCreate);
      attachedFilesId.add(binaryContent.getId());
    }
    Message message = new Message(messageCreateRequest.getContent(),
        channelId,
        userId,
        attachedFilesId);
    messageRepository.save(message);
    // 메세지 작성자를 Online 상태로
    userStatusService.updateByUserId(userId, new UserStatusUpdateRequest(UserStatusType.ONLINE),
        Instant.now());
    return message;
  }

  @Override
  public Message find(UUID messageId) {
    Message message = messageRepository.findById(messageId)
        .orElseThrow(() -> new NoSuchElementException("Message ID: " + messageId + " Not Found"));
    return message;
  }

  @Override
  public List<Message> findAllByChannelId(UUID channelId) {
    if (!channelRepository.existsById(channelId)) {
      throw new NoSuchElementException("Channel ID: " + channelId + " Not Found");
    }

//    // User의 ReadStatus, UserStatus 업데이트
//    // ReadStatus를 현재 시간으로 업데이트
//    ReadStatus readStatus = readStatusService.findByUserIdAndChannelId(userId, channelId);
//    readStatusService.update(readStatus.getId(), new ReadStatusUpdate(Instant.now()));
//    // UserStatus 업데이트 -> 온라인, 현재 활동 중으로 간주
//    userStatusService.updateByUserId(userId, new UserStatusUpdateRequest(UserStatusType.ONLINE),
//        Instant.now());

    Map<UUID, Message> data = messageRepository.findAll();
    return data.values().stream()
//                .filter(message -> message.getChannelId()==channelId)
        /* 왜 ==이 안 됐을까? ==은 메모리 주소가 같은지 확인하는 연산자이고
         *  Message 객체 속 값과 클라이언트로부터 받아온 값은 주소가 같을 수 없으니까
         * 내용의 동등성을 확인하는 equals()가 적절함 */
        .filter(message -> message.getChannelId().equals(channelId))
        .sorted(Comparator.comparing(message -> message.getCreatedAt()))
        .collect(Collectors.toList());
  }


  @Override
  public Message update(UUID messageId, MessageUpdateRequest dto) {
    Message message = messageRepository.findById(messageId)
        .orElseThrow(() -> new NoSuchElementException("Message ID: " + messageId + " Not Found"));
    message.update(dto.getContent());
    messageRepository.save(message);
    return message;
  }

  @Override
  public void delete(UUID messageId) {
    Message message = messageRepository.findById(messageId)
        .orElseThrow(() -> new NoSuchElementException("Message ID: " + messageId + " Not Found"));
    // 첨푸 파일 삭제
    List<UUID> attachedFiles = message.getAttachedFiles();
    for (UUID id : attachedFiles) {
      binaryContentService.delete(id);
    }
    messageRepository.delete(messageId);
  }

}
