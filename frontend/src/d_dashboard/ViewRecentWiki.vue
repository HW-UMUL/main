<script setup>
import axios from 'axios'
import { reactive } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// 토큰 브라우저에서 받아오기
const serverAddress = inject('serverAddress')
const auth = inject('auth')

// 토큰 브라우저에서 받아오기
let authToken = 'Bearer '
let jwtToken = auth

authToken = authToken + auth
const wiki = reactive([])

axios
  .get(`http://${serverAddress}/api/wiki/read/public`, {
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

function readWiki(id) {
  router.push({
    path: `/readwiki/${id}`,
  })
}
</script>

<template>
  <VTable>
    <thead>
      <tr>
        <th class="text-center">Title</th>
        <th class="text-uppercase text-center">Date</th>
      </tr>
    </thead>

    <tbody>
      <tr
        v-for="(item, idx) in rankedItems"
        :key="idx"
      >
        <td class="text-center">
          <a @click="readWiki(item.id)">{{ item.title }}</a>
        </td>
        <td class="text-center">{{ item.date.substring(0, 10) }}</td>
      </tr>
    </tbody>
  </VTable>
</template>
