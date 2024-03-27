# TEAM 1 KMS
![image](https://github.com/final-kms/main/assets/44054359/ee167743-9c9e-4470-9d94-56baa296ac30)

### Frontend
- [x] wiki의 모든 데이터 읽어오기
- [x] wiki 글 작성
- [ ] wiki 상세 페이지 (수정/삭제)

### npm install 
- fontawesome (이미지 버튼 생성용)
 - npm i --save @fortawesome/fontawesome-svg-core    
 - npm i --save @fortawesome/free-solid-svg-icons     // 일반 아이콘에 검은색이 차있는 아이콘
 - npm i --save @fortawesome/vue-fontawesome
 - npm i --save @fortawesome/free-regular-svg-icons   // 일반 아이콘


### import 등
 ```
import { library } from '@fortawesome/fontawesome-svg-core'
import { faUserSecret } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

import { faThumbsUp as farThumsUp } from '@fortawesome/free-regular-svg-icons'
import { faThumbsUp as fasThumsUp } from '@fortawesome/free-solid-svg-icons'

app.mount('#app')

library.add(farThumsUp, fasThumsUp)

app.component('font-awesome-icon', FontAwesomeIcon)


# tiptap3 설치 (글 에디터)
npm install @tiptap/vue-3
npm install @tiptap/starter-kit
npm install @tiptap/extension-underline
npm install @tiptap/extension-placeholder


# button 관련
npm install vue-material-design-icons
```


### 보류
```
// Vuex 설치 (로그인 정보 담기 - 인증 토큰 불러오기 용도 - 보류)
 npm install vuex --save
```

### MEMBER
- 이경민 : PR(Project Manager)
- 김민우 : TL(Tech Leader)
- 박대호 : GM(Git Manager)
- 심규혁 : AC(Agile Coach)

### Team Goal
1) 대략 2주 정도를 한 사이클 시간으로 설정해 작업.
2) 사이클이 끝나면 부족한 부분 보안.
3) 팀원 모두가 프로젝트 프로세스 전 과정 경험.<br>
3-1) 테스트 작업<br>
3-2) (시간 허용 범위 안에서) 팀원 기능 구현 파트 맡아 작업
4) 코드 작업보다 팀원 질문(소통) 우선
5) 코드 규약 작성(대소문자, 케이스 정리 등)
6) 코드 주석 철저히
7) 팀 회의 시간 정하기(오전 할일, 하루 일정 회고 등)
8) main 브런치 이외 본인 브런치에 만들어진 PR은 본인이 합친다.


### Personal Goals
- 민우 : 백엔드와 프론트엔드에 대해 더 깊게 이해하기, CI/CD 경험해보기, 서버에 대한 지식 높이기, 보안과 테스트에 대해 신경쓰기
- 경민 : 프로젝트 전체 이해하기, 차분히 천천히 진행, Issue 및 PR 활용 최대화 하기, 매일 팀프로젝트 회고하고 정리하기
- 대호 : 컴포넌트 기반 개발 이해
- 규혁 : 팀 프로젝트 경험과 개발 이해하기, git 사용 이해하고 익숙해지기


### 개발 목표    
