# <img src="https://github.com/final-kms/main/assets/150432433/b85e9a6c-267c-4788-9aeb-dea171711574" width="28" height="28"> 우리들의 물음표, UMUL

![20240306232331_d45caee97f9b95d9d00cc14d02ce00e3_wo9p](https://github.com/final-kms/main/assets/150432433/9d27f79d-5ed2-4364-8779-df9c8cb10ff3)

(변경될 이미지 입니다)

- 배포 접속 주소 : https://umul.site/
- UMUL Wiki : https://github.com/final-kms/main/wiki


<br>

## 프로젝트 소개
- 설명
- 목표
- 기대효과 등

<br>

## 팀원 구성
| **이경민(PM)** | **김민우(TL)** | **심규혁(AC)** | **박대호(GM)** |
| :------: |  :------: | :------: | :------: |
| [<img src="https://github.com/final-kms/main/assets/150432433/79a75da8-f761-47e6-9460-450f0bac1e7b" height=150 width=150> <br/> @papercrane55123](https://github.com/papercrane55123) | | [<img src="" height=150 width=150> <br/> @](https://github.com/) | | [<img src="" height=150 width=150> <br/> @](https://github.com/) | | [<img src="" height=150 width=150> <br/> @](https://github.com/) | | [<img src="" height=150 width=150> <br/> @](https://github.com/) | 

<br>

## 역할 분담
### ⛵ 이경민
- **Project Manager**
     - 역할에 따른 업무
- **Backend**
     - 개인 업무
- **Frontend**
     - 개인 업무

### 김민우
- **Tech Leader**
     - 역할에 따른 업무
- **Backend**
     - 개인 업무
- **Frontend**
     - 개인 업무

### 심규혁
- **Agile Coach**
     - 역할에 따른 업무
- **Backend**
     - 개인 업무
- **Frontend**
     - 개인 업무
 
### 박대호
- **Git Manager**
     - 역할에 따른 업무
- **Backend**
     - 개인 업무
- **Frontend**
     - 개인 업무

<br>

## 01 개발 환경

<br>

## 02 프로젝트 구조

<br>

## 03 채택한 개발 기술
- axios
- WebSocket 등

<br>

## 04 브런치 전략

<br>

## 05 개발 일정

<br>

## 06 구현 기능 설명

### [초기화면]
- '우물'은 회사 임직원용 서비스 이므로 로그인 후 세부 기능 사용 가능합니다.
- umul.site로 웹 사이트에 접속하면 서비스 시그니처인 우물 디자인과 함께 잠시 후
    - 로그인이 되어 있지 않은 경우 : 로그인 페이지가 나타납니다.
    - 로그인이 되어 있는 경우 : 우물 홈(Dashboard) 페이지가 나타납니다.

| 초기화면 |
|----------|
||

### [회원가입]
- 회원가입은 회원의 아이디, 이메일, 비밀번호가 요구됩니다. (실패 대신 이유 설명 가능?)
- 아이디
     - 20자 이내의 값이 요구됩니다.
     - 대소문자, 한글 모두 입력 가능합니다.
     - 중복되지 않는 유일한 값이어야 합니다.
- 이메일
     - 이메일 형식(예 : example@exam.com)만 입력 가능합니다.
     - 50자 이내의 값이 요구됩니다.
     - 중복되지 않는 유일한 값이어야 합니다.
- 비밀번호
     - 120자 이내의 값이 요구됩니다.
     - JWT로 암호화 되어 DB에 저장됩니다.

| 회원가입 |
|----------|
|![회원가입](https://github.com/final-kms/main/assets/150432433/8d9f8773-4e0e-46ca-846b-a72bdc5987f5)|

### [로그인]
- 로그인은 회원의 아이디와 비밀번호가 요구됩니다.
- 로그인과 비밀번호가 틀리면 '실패'라는 창이 띄워집니다. (실패 대신 이유 설명 가능?) (로그인 materio 없애기, dashboard 깃헙 없애기기)

| 로그인 |
|----------|
|![로그인](https://github.com/final-kms/main/assets/150432433/289172c1-294e-48f1-b747-2d4b357a0a35)|

### [Post 작성]
- Post는 '페이스북', '네이버 지식인'과 같이 자유롭게 질문을 올리고 댓글을 달 수 있는 기능입니다.
- 오른쪽 상단 Post+를 클릭해 작성할 수 있습니다.
- Post 작성은 제목, 본문, 태그가 요구됩니다.
- 태그는 띄어쓰기로 작성 가능하며 submit으로 Post 게시하면 '#'이 붙게 됩니다.
 
| Post 작성 |
|----------|
|![Post 작성](https://github.com/final-kms/main/assets/150432433/ef222881-2abb-40ca-8f09-30167ba7cf4e)|


### [Post 수정 및 삭제]
- 각 게시물에 적용된 '•••'를 클릭하여 수정 및 삭제가 가능합니다.
- 수정 방법은 Post 작성과 동일합니다.
- ~~현재 Post 및 댓글 삭제는 안되는 상황(연결 되어있는 DB 풀어줘야 함)~~

| Post 수정 및 삭제 |
|----------|
||

### [Post 댓글, 추천, 즐겨찾기]
- Post의 본문 내용은 펼치기 및 접기가 가능합니다.
- Post에는 댓글을 달 수 있습니다.
- Post 및 댓글에 추천과 즐겨찾기를 할 수 있습니다.

| Post 댓글, 추천, 즐겨찾기 |
|----------|
|![Post 댓글 작성 좋아요 즐겨찾기](https://github.com/final-kms/main/assets/150432433/caec2a8c-47e2-4e94-b14d-a95e14f64be2)|

### [Post 추천, 즐겨찾기 순위]
- 각 Post 추천 및 즐겨찾기 개수에 따른 순위를 확인할 수 있습니다.
- Post 게시 최신 순으로도 확인할 수 있습니다.
- 각 추천, 즐겨찾기, 최신 순을 클릭시 해당 Post 모달 페이지로 이동 가능 합니다.

| Post 좋아요, 즐겨찾기 순위 |
|----------|
|![Post 좋아요 즐겨찾기 최신순 정렬](https://github.com/final-kms/main/assets/150432433/0489fef0-5e7c-489e-8e76-6cd859c3e49c)|

### []






<br>

## 07 테스트

<br>

## 08 성능 개선

<br>

## 09 프로젝트 후기
### ⛵ 이경민
### 김민우
### 심규혁
### 박대호
