package com.spirnt.mission.discodeit.repository;

import com.spirnt.mission.discodeit.entity.UserStatus;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatusRepository extends JpaRepository<UserStatus, UUID> {


  boolean existsByUserId(UUID userId);

  Optional<UserStatus> findByUserId(UUID userId);
}
