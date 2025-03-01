# Spring 백엔드 트랙 1기 스프린트 미션 제출 리포지토리
# 스프린트 미션 2


1. [목표](#목표)
2. [요구사항💟](#요구사항)
3. [나의 설계⭐](#나의-설계)

## 목표
- 객체 직렬화를 이용해 데이터를 다루는 법을 익힙니다.
  - 채팅 서비스의 기본 구조를 구현한 [스프린트 미션 1](https://github.com/gitSoyoungLee/1-sprint-mission/tree/part1-%EC%9D%B4%EC%86%8C%EC%98%81-sprint1)에 이어 객체 직렬화를 이용하여 저장 로직을 구현합니다.
- 비즈니스 로직과 저장 로직에 대한 이해를 쌓습니다.


## 요구사항💟
### 기본 요구사항
#### File IO를 통한 데이터 영속화
[x] 다음의 조건을 만족하는 서비스 인터페이스의 구현체를 작성하세요.
- 클래스 패키지명: com.sprint.mission.discodeit.service.file
- 클래스 네이밍 규칙: File[인터페이스 이름]
- JCF 대신 FileIO와 객체 직렬화를 활용해 메소드를 구현하세요.

#### 서비스 구현체 분석
[x] JCF*Service 구현체와 File*Service 구현체를 비교하여 공통점과 차이점을 발견해보세요.
- "비즈니스 로직"과 관련된 코드를 식별해보세요.
- "저장 로직"과 관련된 코드를 식별해보세요.

#### 레포지토리 설계 및 구현
[x] "저장 로직"과 관련된 기능을 도메인 모델 별 인터페이스로 선언하세요.
- 인터페이스 패키지명: com.sprint.mission.discodeit.repository
- 인터페이스 네이밍 규칙: [도메인 모델 이름]Repository

[x] 다음의 조건을 만족하는 레포지토리 인터페이스의 구현체를 작성하세요.
- 클래스 패키지명: com.sprint.mission.discodeit.repository.file
- 클래스 네이밍 규칙: File[인터페이스 이름]
- 기존에 구현한 File*Service 구현체의 "저장 로직"과 관련된 코드를 참고하여 구현하세요.

### 심화 요구사항

#### 관심사 분리를 통한 레이어 간 의존성 주입
[x] 다음의 조건을 만족하는 서비스 인터페이스의 구현체를 작성하세요.
- 클래스 패키지명: com.sprint.mission.discodeit.service.basic
- 클래스 네이밍 규칙: Basic[인터페이스 이름]
- 기존에 구현한 서비스 구현체의 "비즈니스 로직"과 관련된 코드를 참고하여 구현하세요.
- 필요한 Repository 인터페이스를 필드로 선언하고 생성자를 통해 초기화하세요.
-  "저장 로직"은 Repository 인터페이스 필드를 활용하세요. (직접 구현하지 마세요.)

## 나의 설계⭐

### 구상
- 왜 객체 직렬화를 쓸까?
  - 객체의 상태를 파일이나 데이터베이스에 저장하고 나중에 복원하고자 할 때 사용한다.
  - 여기서는, ser 피일에 저장하여 데이터베이스처럼 사용한다.
- 저장 로직이란?
  - 데이터를 실제로 저장하거나 갱신하는 부분 (예: Map에 데이터를 추가하거나 업데이트).

### 직렬화

#### 어떤 객체를 직렬화할까?
Serializable 인터페이스를 구현할 클래스
- Common 
- User: 비밀번호는 transient 키워드로 직렬화에서 제외
- Channel
- Message

#### 객체를 어디에 저장할까?
- JCF*Repository는 각각 Map 생성
- File*Repository는 ser 파일 생성
  - user.ser: User 객체를 Map<UUID, User> 형태로 저장
  - channel.ser: Channel 객체를 Map<UUID, Channel> 형태로 저장
  - message.ser: Message 객체를 Map<UUID, Message> 형태로 저장
- 둘 다 Map을 이용하므로 직접적인 저장 로직은 ```put()```, ```remove()```로 구현

#### JCF*Repository, JCF*Service
- 싱글톤과 팩토리 패턴을 유지한다.
- JCF*Service
  - JCFUserService, JCFChannelService, JCFMessageService 
  - 스프린트 미션 1 코드에서 비즈니스 로직 코드는 유지한다.
  - 스프린트 미션 1 코드에서 Map data에 ```put()```하거나 ```remove()``` 등 데이터 저장을 JCF*Repository에 위임한다.
  - 서비스 간 의존 관계가 있다.
- JCF*Repository
  - JCFUserRepository, JCFChannelRepository, JCFMessageRepository
  - 공통적으로 ```save()```, ```delete()```, ```find()``` 메소드를 가진다.
    - ```save()``` : Map에 저장한다.
    - ```delete()```: Map에서 삭제한다.
    - ```find()```: 찾고자 하는 특정 객체 또는 Map 전체를 반환한다.

#### File*Repository, File*Service
- File*Service
  - FileUserService, FileChannelService, FileMessageService
  - JCF*Service와 같은 메소드를 가진다.
  - 비즈니스 로직을 수행한다.
  - File*Repository와 각 서비스들과 의존 관계가 있다.
- File*Repository
  - FileUserRepository, FileChannelRepository, FileMessageRepository
  - 공통적으로 ```save()```, ```delete()```, ```find()``` 메소드를 가진다.
    - ```save()``` : 역직렬화로 Map을 읽고, 객체를 추가하거나 업데이트한 Map을 ser 파일에 직렬화하여 저장한다.
    - ```delete()```: 역직렬화로 Map을 읽고, 객체를 삭제한 Map을 ser 파일에 직렬화하여 저장한다.
    - ```find()```: 역직렬화로 찾고자 하는 특정 객체 또는 Map 전체를 반환한다.

#### Basic*Service
- Basic*Service는 File*Repository와 JCF*Repository의 인터페이스인 Repository를 이용한다. 즉, File*Repository와 JCF*Repositoy 중 원하는 것을 선택하여 사용할 수 있다.
- 레포지토리를 데이터 필드로 선언하고 생성자를 통해 주입한다.
- BasicUserService, BasicChannelService, BasicMessageService
- 레포지토리-서비스 레이어 간 분리가 목적으로, 서비스 간 의존성 주입과 싱글톤 패턴 등 이전 요구사항들 중 일부는 생략하고 CRUD 구현을 중심으로 한다.


