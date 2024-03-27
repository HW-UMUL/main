<script setup>
import axios from 'axios'
import { reactive } from 'vue'

// 토큰 브라우저에서 받아오기
let authToken = 'Bearer '
const cookies = document.cookie.split(';')
let jwtToken = ''

for (let i = 0; i < cookies.length; i++) {
  const cookie = cookies[i].trim()
  // 쿠키 이름이 'jwtToken'으로 시작하는 경우
  if (cookie.startsWith('jwtToken=')) {
    // 'jwtToken'의 값만 추출
    jwtToken = cookie.substring('jwtToken='.length)
    break
  }
}
authToken = authToken + jwtToken

const wiki = reactive([])

axios
  .get(`http://localhost:8080/api/wiki/readall`, {
    headers: {
      Authorization: authToken,
    },
  })
  .then(res => {
    wiki.value = res.data
  })

const rankedItems = computed(() => {
  if (!wiki.value) return [] // wiki.value가 비어 있는 경우 빈 배열 반환

  // date 속성을 기준으로 내림차순으로 wiki.value를 정렬
  const sortedItems = wiki.value.slice().sort((a, b) => {
    return new Date(b.date) - new Date(a.date)
  })

  return sortedItems.slice(0, 5)
})
</script>

<template>
  <VCard>
    <VCardTitle class="text-center">최신 Wiki 게시글</VCardTitle>
    <VCardText style="margin-top: 15px">
      <div style="display: flex">
        <span style="margin-right: 80px">순위</span>
        <span>제목</span>
      </div>
      <div
        style="justify-content: space-between; display: flex; margin: 5px"
        v-for="(item, idx) in rankedItems"
      >
        <div>{{ idx + 1 }}</div>
        <div style="text-align: center; flex: 1">
          <a :href="`http://localhost:5173/readwiki/${item.id}`">{{ item.title }}</a>
        </div>
      </div>
    </VCardText>
  </VCard>
</template>
