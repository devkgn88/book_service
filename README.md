# 📦 Booking Service
회의실 예약 서비스를 담당하는 마이크로서비스

## 🔗 프로젝트 링크
GitHub: https://github.com/devkgn88/booking-service

## 🧩 개요
booking-service는 회의실 예약 정보를 관리하는 백엔드 마이크로서비스입니다.
예약 데이터는 Cassandra에 저장되며, Kafka를 통해 비동기 메시징 처리 후 Elasticsearch에 색인되어 빠른 검색을 지원합니다.
또한 JWT 기반 인증을 통해 보안이 강화되어 있으며, Kibana를 활용한 실시간 모니터링도 가능합니다.

## 🔧 기술 스택
1. Java 17 + Spring Boot 3: RESTful API 백엔드 개발
2. Apache Cassandra: 고성능 분산 데이터 저장소
3. Apache Kafka + Zookeeper: 예약 이벤트 비동기 처리
4. Elasticsearch: 예약 검색 최적화
5. Kibana: Elasticsearch 시각화 및 대시보드
6. JWT (Json Web Token): 사용자 인증 및 권한 관리
7. Docker + Docker Compose: 컨테이너 기반 환경 구성

## 🧠 주요 기능
* 회의실 예약 등록 및 취소
* 날짜 기반 검색 (Elasticsearch)
* Kafka를 통한 비동기 데이터 처리
* JWT 인증으로 안전한 API 호출
* Kibana 대시보드를 통한 예약 모니터링

## 🚀 설치 및 실행
1. 프로젝트 클론
```
git clone https://github.com/devkgn88/booking-service.git
cd booking-service
```
2. docker-compose.yml 파일을 프로젝트 루트에 생성
3. 아래 내용 붙여넣기
```
version: '3.8'
services:
  cassandra:
    image: cassandra:4.1
    container_name: cassandra
    ports:
      - "9042:9042"
    networks:
      - backend

  zookeeper:
    image: confluentinc/cp-zookeeper:7.5.0
    container_name: zookeeper
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
    ports:
      - "2181:2181"
    networks:
      - backend

  kafka:
    image: confluentinc/cp-kafka:7.5.0
    container_name: kafka
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
    networks:
      - backend

  elasticsearch:
    image: elasticsearch:7.17.0
    container_name: elasticsearch
    environment:
      - discovery.type=single-node
      - xpack.security.enabled=false
    ports:
      - "9200:9200"
      - "9300:9300"
    networks:
      - backend

  kibana:
    image: kibana:7.17.0
    container_name: kibana
    ports:
      - "5601:5601"
    environment:
      ELASTICSEARCH_HOSTS: "http://elasticsearch:9200"
    depends_on:
      - elasticsearch
    networks:
      - backend

  booking-service:
    build: .
    container_name: booking-service
    ports:
      - "8080:8080"
    depends_on:
      - cassandra
      - kafka
      - elasticsearch
    environment:
      - SPRING_PROFILES_ACTIVE=prod
    networks:
      - backend

networks:
  backend:
```
4. 도커 컴포즈로 실행
```
docker-compose up --build
```
5. docker에서 cassandra 실행
```
docker exec -it cassandra bash -c "cqlsh -u cassandra -p cassandra"
```
6. cassandra에 keyspace 생성 및 접근
```
CREATE KEYSPACE spring_cassandra WITH replication = {'class' : 'SimpleStrategy', 'replication_factor' : 1};
USE spring_cassandra;
```
7. table 생성
```
CREATE TABLE booking(id UUID PRIMARY KEY, room_id int, room_name text, title text, start_time timestamp, end_time timestamp);
```

## 📫 문의
이메일: devkgn88@gmail.com
