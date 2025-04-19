package com.spirnt.mission.discodeit.repository;

import com.spirnt.mission.discodeit.entity.BinaryContent;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BinaryContentRepository extends JpaRepository<BinaryContent, UUID> {

}
