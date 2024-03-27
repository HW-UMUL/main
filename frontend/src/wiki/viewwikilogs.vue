<script setup>
import axios from 'axios'
import { useRoute, useRouter } from 'vue-router'

// 토큰 브라우저에서 받아오기
let authToken = 'Bearer '
const cookies = document.cookie.split(';')
let jwtToken = ''

for (let i = 0; i < cookies.length; i++) {
  const cookie = cookies[i].trim()
  if (cookie.startsWith('jwtToken=')) {
    // 'jwtToken'의 값만 추출
    jwtToken = cookie.substring('jwtToken='.length)
    break
  }
}
authToken = authToken + jwtToken

const route = useRoute()
const router = useRouter()
const logs = reactive([])
let date = reactive([])

axios
  .get(`http://localhost:8080/api/wikilog/readall/${route.params.id}`, {
    headers: {
      Authorization: authToken,
    },
    withCredentials: true,
  })
  .then(res => {
    logs.value = res.data
    console.log(logs.value)

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

function viewMore(content) {
  let container = document.getElementById("container")
  let btn = document.getElementById("moreBtn")
  let full = content
  let less = full.substring()
}
</script>

<template>
  <h2 style="margin-left: 12px">{{ route.params.id }}번 게시글 변경 이력</h2>
  <br />
  <VCol
    v-for="(item, idx) in logs.value"
    :key="idx"
  >
    <hr />
    <div style="display: flex; justify-content: space-between">
      <div style="text-align: left; color: #905dff; margin: 10px">
        {{ item?.tag }}
      </div>
    </div>
    <div
      width="100%"
      style="color: gray"
    >
      <h1>{{ item?.title }}</h1>
      <p>&nbsp; 변경자: who &nbsp; {{ date[idx] }}에 변경</p>
    </div>
    <br />
    <div>
      <p
        id="container"
        v-html="`${item?.content}`"
      ></p>
    </div>
    <button
      id="morebtn"
      @click="viewMore()"
    >
      더 보기
    </button>
    <br /><br /><br /><br />
  </VCol>
  <div align="right">
    <button
      @click="cancelForm"
      style="
        background-color: #905dff;
        border: none;
        color: white;
        padding: 7px 15px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        border-style: solid;
        font-size: 15px;
      "
    >
      뒤로가기
    </button>
  </div>
</template>

<style scoped>
#container {
  display: inline;
}

#btn {
  border: none;
  background-color: transparent;
  padding: 0;
  font-size: 15px;
  cursor: pointer;
}

#btn.hidden {
  display: none;
}
</style>
