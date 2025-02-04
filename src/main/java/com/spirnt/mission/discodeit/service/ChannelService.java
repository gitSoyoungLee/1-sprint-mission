package com.spirnt.mission.discodeit.service;

import com.spirnt.mission.discodeit.dto.ChannelDto;
import com.spirnt.mission.discodeit.enity.Channel;
import com.spirnt.mission.discodeit.enity.ChannelType;
import com.spirnt.mission.discodeit.enity.User;
import com.spirnt.mission.discodeit.repository.ChannelRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChannelService {
    void setChannelRepository(ChannelRepository channelRepository);
    void setService(UserService userService, MessageService messageService);
    // Create
    UUID createChannel(String name, String description, ChannelType type);    // 채널 생성

    // Read
    List<ChannelDto> getAllChannelsInfo(); // 전체 채널 다건 조회

    ChannelDto getChannelInfoById(UUID channelId);  // 채널 정보 단건 조회

    // Update
    void updateChannelName(UUID channelId, String name);   // 채널명 변경

    void updateChannelDescription(UUID channelId, String description);     //채널 설명 변경

    // Delete
    void deleteChannel(UUID channelId);    // 채널 삭제

    void addUserIntoChannel(UUID channelId, UUID userId);    // 유저가 채널에 입장

    void deleteUserInChannel(UUID channelId, UUID userId);   // 유저를 채널에서 삭제

    void deleteUserInAllChannels(UUID userId);  // 유저를 모든 채널에서 삭제(유저 삭제를 위한 메소드)

    Optional<Channel> findById(UUID channelId);


}
