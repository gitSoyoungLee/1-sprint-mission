package com.spirnt.mission.discodeit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 문제 상황: @WebMvcTest는 @EnableJpaAuditing에 필요한 JPA 관련 빈을 로딩하지 않음 그런데 DiscodeitApplicaiton에서
 * @EnableJpaAuditing을 쓰고 있어 테스트 시 해당 어노테이션을 로딩하려고 함 JPA 빈이 없어 오류 발생 -> 해결: DiscodeitApplication에서
 * @EnableJpaAuditing을 지우고 따로 config로 분리해서 테스트 때 빈이 자동 로딩되지 않게 함
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {

}