package com.spirnt.mission.discodeit.repository;

import com.spirnt.mission.discodeit.entity.Channel;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, UUID> {

  @Query("SELECT c from Channel c WHERE c.type = 'PUBLIC'")
  List<Channel> findAllPublic();
}
