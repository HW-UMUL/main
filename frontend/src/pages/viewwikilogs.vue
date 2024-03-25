<script setup>
import axios from 'axios'
import { useRoute } from 'vue-router'
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
const logs = reactive([])

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
  })
</script>

<template>
  <h2>변경 이력</h2>
  <br />
  <table
    border="0"
    style="width: 50%"
    height=""
  >
    <tr>
      <td>Log id</td>
      <td>title</td>
      <td>content</td>
    </tr>
    <tr v-for="(item, idx) in logs.value">
      <td>{{ item.id }}</td>
      <td>{{ item.title }}</td>
      <td v-html="`${item.content}`"></td>
    </tr>
  </table>
</template>
