package com.spirnt.mission.discodeit.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;


public class MDCLoggingInterceptor extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    try {
      // 요청 ID 생성
      String requestId = UUID.randomUUID().toString();
      MDC.put("requestId", requestId);
      MDC.put("requestMethod", request.getMethod());
      MDC.put("requestUrl", request.getRequestURI());
      // 요청 ID를 응답 헤더에 포함
      response.addHeader("Discodeit-Request-ID", requestId);
      filterChain.doFilter(request, response);
    } finally {
      MDC.clear();
    }
  }
}
