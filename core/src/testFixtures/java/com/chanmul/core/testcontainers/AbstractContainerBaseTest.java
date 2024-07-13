package com.chanmul.core.testcontainers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers(disabledWithoutDocker = true)
public abstract class AbstractContainerBaseTest {
  // redis docker image https://velog.io/@jsb100800/Spring-boot-Test
  @Container
  private static final GenericContainer REDIS_CONTAINER =
      new GenericContainer<>("redis:7.2.5-alpine")
          .withExposedPorts(6379)
          .withReuse(true);

  // 동적 설정값 매핑
  @DynamicPropertySource
  public static void overrideProps(DynamicPropertyRegistry registry) {
    registry.add("spring.data.redis.host", REDIS_CONTAINER::getHost);
    registry.add("spring.data.redis.port", () -> "" + REDIS_CONTAINER.getMappedPort(6379));
  }

  @BeforeAll
  static void beforeAll() {
    REDIS_CONTAINER.start();
  }

  @AfterAll
  static void afterAll() {
    REDIS_CONTAINER.stop();
  }
}