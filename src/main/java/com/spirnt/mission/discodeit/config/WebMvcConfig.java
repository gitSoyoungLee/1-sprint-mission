package com.spirnt.mission.discodeit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebMvcConfig {

  @Bean
  public MDCLoggingInterceptor mdcLoggingInterceptor() {
    return new MDCLoggingInterceptor();
  }
}
