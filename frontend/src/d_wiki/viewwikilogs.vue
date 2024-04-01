<script setup>
import axios from 'axios'
import { useRoute, useRouter } from 'vue-router'

// 토큰 브라우저에서 받아오기
const serverAddress = inject('serverAddress')
const auth = inject('auth')

// 토큰 브라우저에서 받아오기
let authToken = 'Bearer '
let jwtToken = auth

authToken = authToken + jwtToken

const route = useRoute()
const router = useRouter()

const logs = reactive([])
let date = reactive([])
const showMore = reactive([])

axios
  .get(`http://${serverAddress}/api/wikilog/readall/${route.params.id}`, {
    headers: {
      Authorization: authToken,
    },
    withCredentials: true,
  })
  .then(res => {
    logs.value = res.data

    for (let i = 0; i < logs.value.length; i++) {
      date[i] = new Date(logs.value[i].date)

      date[i] =
        date[i].getFullYear() +
        '-' +
        (date[i].getMonth() + 1).toString().padStart(2, '0') +
        '-' +
        date[i].getDate().toString().padStart(2, '0') +
        ' ' +
        date[i].getHours().toString().padStart(2, '0') +
        ':' +
        date[i].getMinutes().toString().padStart(2, '0') +
        ':' +
        date[i].getSeconds().toString().padStart(2, '0')
    }
  })

const cancelForm = () => {
  // 이전 페이지로 이동
  router.go(-1)
}

const truncateContent = (content, maxLength) => {
  if (content.length <= maxLength) {
    return content
  }
  // 이미지 주소를 추출하기 위한 정규식 패턴
  const imageUrlRegex = /https?:\/\/(?:[a-zA-Z]|[0-9]|[$-_@.&+]|[!*\\(\\),]|(?:%[0-9a-fA-F][0-9a-fA-F]))+/g
  // 최대 길이까지의 텍스트를 추출
  const truncatedText = content.substring(0, maxLength)
  // 이미지 주소를 제외한 텍스트만 반환
  return truncatedText.replace(imageUrlRegex, '')
}

const hasMoreContent = content => {
  return content.length > 200
}

const toggleMore = index => {
  showMore[index] = !showMore[index]
}
</script>

<style scoped>
.hr-sect {
  display: flex;
  flex-basis: 100%;
  align-items: center;
  font-size: 15px;
  font-weight: bold;
  margin: 8px 0px;
}
.hr-sect::before,
.hr-sect::after {
  content: '';
  flex-grow: 1;
  background: rgba(0, 0, 0, 0.35);
  height: 1px;
  font-size: 0px;
  line-height: 0px;
}
</style>

<template>
  <h2>{{ route.params.id }}번 게시글 변경 이력</h2>
  <hr />
  <br />
  <div
    v-for="(item, idx) in logs.value"
    :key="idx"
    style="margin-top: 10px; margin-bottom: 40px"
  >
    <div
      width="100%"
      style="color: gray"
    >
      <h1>{{ item.title }}</h1>
      <p>&nbsp;{{ item.title }} &nbsp; {{ date[idx] }}에 변경</p>
    </div>
    <br />
    <!-- 첫 번째 부분만 보이도록 수정 -->
    <div
      v-if="showMore[idx]"
      v-html="item?.content"
      style="width: 100%; height: 50%"
    ></div>

    <!-- 접는 버튼 추가 -->
    <div
      v-if="showMore[idx]"
      style="text-align: center; margin-top: 60px"
      class="hr-sect"
    >
      <button
        style="background-color: white; border-radius: 20px; width: 90px; height: 41px; border: 2px solid"
        @click="toggleMore(idx)"
      >
        - 접기
      </button>
    </div>

    <div v-else>
      <!-- 이미지 주소가 포함되어 있는 경우 -->
      <div v-html="truncateContent(item?.content, 200)"></div>
      <div
        v-if="hasMoreContent(item?.content)"
        style="
          justify-content: center;
          position: relative; /* 상대 위치 지정 */
          display: flex; /* 버튼과 가로줄을 가로로 나란히 배치하기 위해 */
        "
      >
        <div
          style="text-align: center; margin-top: 30px"
          class="hr-sect"
        >
          <button
            style="background-color: white; border-radius: 20px; width: 90px; height: 41px; border: 2px solid"
            @click="toggleMore(idx)"
          >
            + 더 보기
          </button>
        </div>
      </div>
      <div
        v-else
        style="margin-top: 50px"
      >
        <hr />
      </div>
    </div>
  </div>
  <br />

  <!-- <table
    border="1"
    style="width: 50%"
    height=""
  >
    <tr align="center">
      <td>Log ID</td>
      <td>날짜</td>
      <td>제목</td>
      <td>내용</td>
      <td>변경 후 내용</td>
    </tr>
    <tr
      align="center"
      v-for="(item, idx) in logs.value"
    >
      <td>{{ item.id }}</td>
      <td>{{ date[idx] }}</td>
      <td>{{ item.title }}</td>
      <td v-html="`${item.content}`"></td>
      <td>{{ item.category }}</td>
    </tr>
  </table> -->
  <br />
  <VBtn @click="cancelForm">뒤로가기</VBtn>
</template>
