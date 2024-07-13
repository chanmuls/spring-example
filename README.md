---

# Spring Example

이 레포지토리는 Spring 프레임워크의 다양한 기능과 모범 사례를 보여주는 샘플 Spring 애플리케이션을 포함하고 있습니다.

## 스펙 및 주요 기능

- **프레임워크**: Spring Boot
- **언어**: Java
- **빌드 도구**: Gradle
- **아키텍처**: 멀티 모듈 프로젝트
  - **Core**: 핵심 비즈니스 로직 포함
  - **Domain**: 도메인 모델 및 엔티티
  - **Client**: 외부 서비스 클라이언트
  - **Facade**: 서비스 호출을 추상화하는 파사드 레이어
  - **Application**: 메인 Spring Boot 애플리케이션 모듈
- **Docker 지원**: 컨테이너화를 위한 Docker 구성
- **Lombok**: 보일러플레이트 코드를 줄이기 위한 도구

## 설치 방법

1. **레포지토리 클론**
   ```sh
   git clone https://github.com/chanmuls/spring-example.git
   cd spring-example
   ```

2. **프로젝트 빌드**
   ```sh
   ./gradlew build
   ```

3. **애플리케이션 실행**
   ```sh
   ./gradlew bootRun
   ```

4. **Docker 사용**
   - Docker가 설치 및 실행 중인지 확인하세요.
   - Docker 이미지 빌드:
     ```sh
     docker build -t spring-example .
     ```
   - Docker 컨테이너 실행:
     ```sh
     docker run -p 8080:8080 spring-example
     ```

## 사용법

- 애플리케이션에 접속: `http://localhost:8080`

## 라이선스

이 프로젝트는 MIT 라이선스에 따라 라이선스가 부여됩니다. 자세한 내용은 [LICENSE](LICENSE) 파일을 참조하세요.

---