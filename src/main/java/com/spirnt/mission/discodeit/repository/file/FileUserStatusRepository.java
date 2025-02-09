package com.spirnt.mission.discodeit.repository.file;

import com.spirnt.mission.discodeit.enity.UserStatus;
import com.spirnt.mission.discodeit.repository.UserStatusRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public class FileUserStatusRepository extends FileRepository implements UserStatusRepository {

    public FileUserStatusRepository(String fileDirectory) {
        super(fileDirectory + "/UserStatus");
    }

    @Override
    public void save(UserStatus userStatus) {
        Path path = resolvePath(userStatus.getId());
        saveToFile(path, userStatus);
    }

    @Override
    public void delete(UUID id) {
        Path path = resolvePath(id);
        deleteFile(path);
    }

    @Override
    public Optional<UserStatus> findById(UUID id) {
        Path path = resolvePath(id);
        return loadFromFile(path);
    }

    @Override
    public Map<UUID, UserStatus> findAll() {
        Map<UUID, UserStatus> map = new HashMap<>();
        // 폴더 내 모든 .ser 파일을 찾음
        try (Stream<Path> paths = Files.walk(this.getDIRECTORY())) {
            paths.filter(path -> path.toString().endsWith(".ser"))  // .ser 파일만 필터링
                    .forEach(path -> {
                        Optional<UserStatus> userStatus = loadFromFile(path);
                        userStatus.ifPresent(us -> map.put(us.getId(), us));
                    });
        } catch (IOException e) {
            System.out.println("파일을 읽는 중 오류가 발생했습니다: " + e.getMessage());
        }

        return map;
    }
}
