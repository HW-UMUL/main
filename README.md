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

```


### 보류
```
// Vuex 설치 (로그인 정보 담기 - 인증 토큰 불러오기 용도 - 보류)
 npm install vuex --save
```