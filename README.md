![createtable](https://github.com/final-kms/main/assets/76561901/759afa66-8622-40b9-a128-2163234e2d7e)# <img src="https://github.com/final-kms/main/assets/150432433/b85e9a6c-267c-4788-9aeb-dea171711574" width="28" height="28"> 우리들의 물음표, UMUL

![20240306232331_d45caee97f9b95d9d00cc14d02ce00e3_wo9p](https://github.com/final-kms/main/assets/150432433/9d27f79d-5ed2-4364-8779-df9c8cb10ff3)

~~(변경될 이미지 입니다)~~

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
- 회원가입은 회원의 아이디, 이메일, 비밀번호가 요구됩니다. ~~(실패 대신 이유 설명 가능?)~~
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
- 로그인과 비밀번호가 틀리면 '실패'라는 창이 띄워집니다. ~~(실패 대신 이유 설명 가능?) (로그인 materio 없애기, dashboard 깃헙 없애기기)~~

| 로그인 |
|----------|
|![로그인](https://github.com/final-kms/main/assets/150432433/289172c1-294e-48f1-b747-2d4b357a0a35)|

### [로그아웃]
- 로그아웃은 로그아웃 버튼 클릭시 즉시 적용됩니다.
     - 첫번째 버튼 : 왼쪽 Nav Logout 버튼
     - 두번째 버튼 : 오른쪽 상단 프로필의 Logout 버튼

| 로그아웃 |
|----------|
|![로그아웃](https://github.com/final-kms/main/assets/150432433/c52e6bb6-2e69-4f9d-a02b-4ad0cfae8b3b)|


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


## [Wiki]
- Wiki는 업무에 관한 정보를 카테고리로 관리하는 시스템입니다.
  
 
### [Wiki 게시글 작성]
- Wiki 게시글 작성 시 제목, 내용, 카테고리를 작성하여야 합니다.
- 세 가지 중 하나라도 작성하지 않으면 알림을 발생시키며, 게시글 작성이 원활하게 수행되지 않습니다.
- tiptap 에디터를 활용하여 글의 굵기, 크기 등의 효과를 설정할 수 있습니다.

| Wiki 게시글 작성 |
|----------|
![WikiWrite](https://github.com/final-kms/main/assets/76561901/2894cd07-3882-4354-86b7-b3544210aed8)

### [Wiki 게시글 열람] 
- 게시글을 5개, 10개, 20개씩 볼 수 있도록 선택할 수 있습니다.
- 페이지 당 게시글의 개수를 초과하면 다음 버튼을 클릭하여 이후의 게시글을 열람할 수 있도록 합니다.

| Wiki 게시글 열람 |
|----------|
![PagingWiki](https://github.com/final-kms/main/assets/76561901/eafb391d-e9bf-4415-8f90-1e5a8debf2fd)

### [Wiki 게시글 수정]
- Wiki 게시글 수정 시에도 작성 시와 마찬가지로 빈 요소를 허용하지 않습니다.
- 게시글 수정 시 로그를 저장하여 이력 관리를 할 수 있습니다.

| Wiki 게시글 수정 |
|----------|
![WikiUpdate](https://github.com/final-kms/main/assets/76561901/22ee0bd4-c2ed-438d-be19-e951ea563376)


### [Wiki 게시글 삭제]
- 게시글 삭제 시 "정말 삭제하시겠습니까?" 라는 문구와 함께 알림을 발생시켜 삭제 여부를 묻습니다.
- 작성자, 관리자 외에 게시글을 삭제하지 못하도록 합니다.

| Wiki 게시글 삭제 |
|----------|
![WikiDelete](https://github.com/final-kms/main/assets/76561901/6ccdf810-f6c9-429f-80fd-41892a9fa8b1)



### [Wiki 게시글 좋아요/즐겨찾기 기능]
- 게시글에 좋아요/즐겨찾기 버튼을 누를 수 있습니다.
- 좋아요/즐겨찾기 개수를 세어 순위를 매길 수 있습니다.
- 좋아요/즐겨찾기 표시를 한 게시글은 마이페이지 내에 표출될 수 있습니다.

| Wiki 게시글 좋아요/즐겨찾기 기능 |
|----------|
![LikeStar](https://github.com/final-kms/main/assets/76561901/d0818662-d57c-4248-88f4-574e0127244a)

### [마이페이지 My Info]
- 마이페이지 My Info 에서는 개인 정보를 수정할 수 있습니다. ~~(Account Setting 이름 변경하기)~~
     - 원하는 이미지(파일)를 선택해 프로필을 변경할 수 있습니다.~~(프로필 변경시 화면이 꺼지는 현상 있음)(프로필 밀림 현상 있음음)~~
     - 회원 아이디, 이메일, 비밀번호 모두 수정 가능합니다.
     - 해당 수정에 대한 조건은 회원가입 조건과 동일합니다.

| 마이페이지 My Info |
|----------|
|![마이페이지 My Info](https://github.com/final-kms/main/assets/150432433/8f20a6c5-c992-42c8-8274-d092399b1651)|

### [마이페이지 My Post]
- 마이페이지 My Post 에서는 '내가' 작성한 Post를 확인할 수 있습니다.
- My Post의 내용은 기본 Post 페이지 내용과 동일합니다.

| 마이페이지 My Post |
|----------|
|![마이페이지 My Post](https://github.com/final-kms/main/assets/150432433/f5c8587a-92aa-4538-b703-035f6f979dd4)|


### [마이페이지 My Wiki]
- 마이페이지 My Wiki 에서는 '내가' 작성한 Wiki를 확인할 수 있습니다.
- My Wiki의 내용은 기본 Wiki 페이지 내용과 동일합니다.

| 마이페이지 My Wiki |
|----------|
|![마이페이지 My Wiki](https://github.com/final-kms/main/assets/150432433/9faaeff9-a1b3-4a23-87c6-c96a764d6310)|


### [마이페이지 My Reply]
- 마이페이지 My Reply 에서는 '내가' 작성한 Reply를 확인할 수 있습니다.
- 댓글을 Post에 달리기 때문에 My Reply 에서는 Post가 보여집니다. 
- My Reply의 내용은 내가 작성한 댓글이 포함된 기본 Post 페이지 값과 동일합니다.

| 마이페이지 My Reply |
|----------|
|![My Reply](https://github.com/final-kms/main/assets/150432433/875e223b-e580-4ff1-8066-d0f2b40413fe)|


### [마이페이지 Like]
- 마이페이지 Like 에서는 '내가' 추천한 Post와 Wiki를 확인할 수 있습니다.~~(위키는 작동 안됨)~~

| 마이페이지 Like |
|----------|
|![마이페이지 Like](https://github.com/final-kms/main/assets/150432433/c8f8e0d1-4b2f-4e39-aaf4-7466c865d5c5)|


### [마이페이지 Star]
- 마이페이지 Star 에서는 '내가' 즐겨찾기한 Post와 Wiki를 확인할 수 있습니다.~~(위키는 작동 안됨)~~

| 마이페이지 Star |
|----------|
|![마이페이지 Star](https://github.com/final-kms/main/assets/150432433/9764cc0b-c6e5-435a-8a9b-69034821550d)|

### [Follow]
- 원하는 사용자를 Follow, UnFollow할 수 있습니다.
- 사용자의 email을 검색해 Follow 기능을 이용할 수 있습니다.
- 사용자 email의 일부만 입력해도 검색이 가능합니다. ~~(Follow 검색시 해당 사용자 프로필 이미지 출력 안됨. 기본 이미지 나옴)~~

| Follow |
|----------|
|![Follow](https://github.com/final-kms/main/assets/150432433/6aae6318-b9a0-4cfa-bffc-bb1636c36bec)|


### [Followee] 
- Followee 기능을 통해 나를 follow한 사용자를 확인할 수 있습니다. ~~(folowee 오타있음)~~

| Followee |
|----------|
|![Followee](https://github.com/final-kms/main/assets/150432433/040ec2a7-183e-48d5-89fe-efee479b52bb)|


## [조직]
- 조직(Table)은 사용자가 속한 조직을 나타내기 위한 기능입니다.
- 조직원들이 작성한 글을 열람할 수 있습니다.
- private 상태의 조직에서 글을 작성하면 조직 외부의 사용자들은 그 조직의 게시글을 열람할 수 없습니다. 


### [조직 생성]
- 조직의 이름, 설명을 작성하여 조직을 생성할 수 있습니다.

| 조직 생성 |
|----------|
![createtable](https://github.com/final-kms/main/assets/76561901/4913a3b2-d9b9-4acd-81a4-af46703e5b40)

 

### [조직 초대]
- 초대할 사람의 이메일을 기입하여 조직에 초대할 수 있습니다.
- 초대를 받은 사람은 초대를 수락할 수도, 거절할 수도 있습니다.
- 초대를 수락하여 조직원이 되면 조직 내에서 게시글을 작성할 수 있고, 작성된 게시글은 조직 내에 표출됩니다.

| 조직 초대 |
|----------|
![invite](https://github.com/final-kms/main/assets/76561901/f7b2cde6-d0b1-471a-9a43-afcefd2cce0b)


### [조직 관리]
- 관리자는 조직의 이름, 설명을 변경할 수 있고, 관리자를 설정할 수 있습니다.
- 관리자는 조직의 public/private 상태를 변경할 수 있습니다.
- 관리자는 조직에 새로운 조직원을 초대할 수 있습니다.

| 조직 관리 |
|----------|
![manageTable](https://github.com/final-kms/main/assets/76561901/a7e4268c-6df3-4650-8383-2c562e3d9d60)

### [조직 관리자 설정]
- 관리자로 설정할 사람의 이메일을 작성하여 관리자로 임명/해임할 수 있습니다.

| 조직 관리자 설정 |
|----------|
![admin](https://github.com/final-kms/main/assets/76561901/94e31826-3f83-45f9-8c06-4c29bf62a088)


### [ChatRoom] 
- 채팅방 생성
     - 채팅방 생성이 가능합니다.
     - null 값이 아닌 이름으로 채팅방을 생성해야 합니다.

| ChatRoom |
|----------|
|![Create Chat Room](https://github.com/final-kms/main/assets/150432433/a9124d86-fd64-4188-81ed-0303e48b2108)|

### [Chat]
- 채팅하기
     - 사용자를 이메일로 초대할 수 있습니다.
     - 글 작성시 Enter 클릭은 줄 바꿈입니다.
     - 긴 글 작성 후 제출 시, 길이에 맞춰 줄이 자동으로 넘어 갑니다.
     - 작성 시간과 작성자를 확인할 수 있습니다.

| Chat |
|----------|
|![Chatting](https://github.com/final-kms/main/assets/150432433/3f303602-bfc7-4b93-84c5-f2253b0e56d6)|

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
