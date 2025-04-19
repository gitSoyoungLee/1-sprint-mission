package com.spirnt.mission.discodeit.controller;

import com.spirnt.mission.discodeit.controller.swagger.AuthApiDocs;
import com.spirnt.mission.discodeit.dto.auth.LoginRequest;
import com.spirnt.mission.discodeit.dto.user.UserDto;
import com.spirnt.mission.discodeit.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController implements AuthApiDocs {

  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<UserDto> login(@Valid @RequestBody LoginRequest loginRequest) {
    log.info("[Login Request Started]");
    UserDto user = authService.login(loginRequest);
    log.info("[Login Request Completed]");
    return ResponseEntity.ok(user);
  }
}
