# 🛵 배달 서비스
- 여러 음식점의 요리를 배달시켜 먹을 수 있는 서비스
- 사용자는 여러 가게의 정보를 찾아보고, 원하는 가게의 음식을 주문 가능
- 가게의 사장님은 가게를 만들고, 메뉴를 추가해 손님을 모을 수 있음

# ⭐ 기능
- 사용자
  - 회원가입
  - 회원탈퇴
  - 로그인
  - 로그아웃
- 가게
  - 생성
  - 수정
  - 폐업
  - 가게명으로 전체 조회 (메뉴 미포함)
  - 단건 조회 (메뉴 포함)
- 메뉴
  - 생성
  - 수정
  - 삭제
- 주문
  - 하나의 메뉴만 주문 가능
  - 사장님은 주문을 수락할 수 있으며 배달이 완료될 때까지의 모든 상태를 순서대로 변경
- 리뷰
  - 생성
  - 가게 정보를 기준으로 다건 조회
  - 별점 범위에 따라 조회

# 🚨 주의사항
- 일반 유저(USER) 또는 사장님(OWNER)으로만 가입 가능
- 중복된 사용자 아이디로 가입 불가
- 탈퇴 전 비밀번호 확인 필수
- 탈퇴한 사용자의 아이디는 재사용 할 수 없고, 복구도 불가
- 사장님 권한만 가게 생성 가능
- 사장님은 폐업 상태가 아닌 가게를 최대 3개까지만 운영 가능
- 오픈 및 마감 시간 사이에만 주문 가능
- 폐업된 가게 조회 불가
- 메뉴 생성, 수정, 삭제는 해당 가게 사장님만 가능
- 가게에서 설정한 최소 주문 금액을 만족해야 주문이 가능
- 가게의 오픈/마감 시간이 지나면 주문 불가
- 배달 완료 되지 않은 주문은 리뷰 작성 불가
- 본인이 작성한 리뷰는 보이지 않음
- 고객은 주문 건에 대해 리뷰 1건만 작성 가능

# 👩‍💻 기능별 담당자
|담당자|기능|블로그 주소|깃허브 주소|
|:----|:----|:----|:----|
|한승완|1. 사용자 CUD </br> 2. 로그인 및 로그아웃 </br> 3. 주문 CRU|https://velog.io/@swhan98/posts|https://github.com/Dawnfeeling|
|김지연|1. 가게 CRUD|https://velog.io/@yeoni9094/posts </br> https://blog.naver.com/yeondata|https://github.com/jiyeon0926|
|이한준|1. 메뉴 CUD|https://velog.io/@vaxee_/posts|https://github.com/Vaxee03|
|김태훈|1. 리뷰 CR|https://view0576.tistory.com/|https://github.com/view0576|

-------------------

# 📄 API 명세서

## 1. 사용자
|기능|Method|URL|request header|request|response|상태 코드|
|:----|:---:|:----|:----|:----|:----|:----|
|회원가입|POST|/users/signup|POST /users/signup HTTP/1.1 </br> Content-Type: application/json|{</br> "email": "user1@naver.com", </br> "name": "유저1", </br> "password": "User*1234", </br> "role": "OWNER" </br>}|{</br>"id": 1, </br> "name": "유저1", </br> "email": "user1@naver.com", </br> "role": "OWNER" </br>}|201 Created </br> 400 Bad Request|
|비밀번호 수정|PATCH|/users|PATCH /users HTTP/1.1 </br> Cookie: JSESSIONID= ${sessionId}|{</br> "oldPassword": "User1234", </br> "newPassword": "User1235" </br>}||200 OK </br> 404 Not Found </br> 401 Unauthorized|
|회원탈퇴|DELETE|/users|DELETE /users HTTP/1.1 </br> Cookie: JSESSIONID= ${sessionId}|{</br>"password": "User*1234" </br>}||204 No Content </br> 400 Bad Request </br> 401 Unauthorized|
|로그인|POST|/login|POST /login HTTP/1.1 </br> Content-Type: application/json </br> Cookie: JSESSIONID= ${sessionId}|{</br>"email": "user1@naver.com", </br>"password": "User*1234" </br>}|{</br>"id": 1, </br> "name": "유저1", </br> "email": "user1@naver.com", </br> "role": "OWNER" </br>}|200 OK </br> 400 Bad Request </br> 401 Unauthorized|
|로그아웃|POST|/logout|POST /logout HTTP/1.1 </br> Cookie: JSESSIONID= ${sessionId}|||200 OK </br> 401 Unauthorized|

## 2. 가게

## 3. 메뉴

## 4. 주문

## 5. 리뷰
|기능|Method|URL|request header|request|response|상태 코드|
|:----|:---:|:----|:----|:----|:----|:----|
|리뷰 작성|POST|/stores/{storeId}/{orderId}/reviews|GET /stores/1/reviews HTTP/1.1 </br> Cookie: JSESSIONID= ${sessionId}|{</br> "comment": “맛없어요”, </br> "rating": 1</br>}|{</br>"id": 1, </br> "menu": "엽기떡볶이 2인", </br> "name": "user1", </br> "comment": "맛없어요", </br> "rating": 1, </br> "midifiedAt": 2024-12-06T01:58:10.0335959, </br>}|204 No Content </br> 401 Unauthorized </br> 404 Not Found|
|리뷰 전체 조회|GET|/stores/{storeId}/reviews|GET /stores/1/reviews HTTP/1.1 </br>Cookie: JSESSIONID= ${sessionId}||[</br>{</br>"id": 1,</br>"menu": "엽기떡볶이 2인",</br>"name": "user1",</br>"comment": "맛없어요",</br>"rating": 1,</br>"midifiedAt": 2024-12-06T01:58:10.0335959,</br>},</br>{</br>"id": 2,</br>"menu": "엽기떡볶이 2인",</br>"name": "user2",</br>"comment": "맛있어요",</br>"rating": 5,</br>"midifiedAt": 2024-12-06T01:58:10.0335959,</br>}</br>]|200 OK </br> 401 Unauthorized|

-----------------

# ☁ ERD
<img src="https://github.com/user-attachments/assets/6e99f96e-1512-4260-a038-45db038c9226">
