## 🛒JPA 학습을 위한 쇼핑몰 클론 코딩 
---
## 프로젝트 설명
- 스프링부트를 이용하여 제작하는 쇼핑몰 입니다.
- 판매자와 구매자 기능을 별도로 분리하여 제작하였습니다 
    - 로그인 세션을 이용하여 역할에 따른 구매자와 판매자의 페이지가 랜더링 되도록 구현
  
## 🛠️ 사용 기술 및 개발 환경
개발환경
- 운영체제 : mac M1
- IDE : Intelli J
- JDK : JDK 11
- Spring Boot : 2.52
- Database : Mysql, H2(testDB)

FE
- thymeleaf
- html, css, js
- bootStrap

BE
- Spring Boot, Spring Security, SrpingJpa
- JUnit5, Mockito

--- 
DB 설계
- Member : 사이트를 이용하는 가입자, 관리자 계정을 관리
- Product : 상품에 대한 정보들을 관리
- ProductImg : 상품 이미지 정보들을 관리
- Order : 주문에 대한 정보들을 관리
- OrderItem : 주문한 상품에 대한 정보들을 관리
- Cart : 장바구니에 대한 정보들을 관리
- CartItem : 장바구니에 등록한 상품들에 대한 정보들을 관리
- BaseEntity : 공통된 멤버 변수들을 관리
- BaseTimeEntity : 공통된 멤버 변수들을 관리2

Infra
- 