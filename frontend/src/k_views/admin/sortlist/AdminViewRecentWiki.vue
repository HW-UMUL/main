<script setup>
import axios from 'axios'
import { reactive } from 'vue'
import { useRouter } from 'vue-router'

////////////////////
// 년-월-일
const formatDate = function(value) {
    const date = new Date(value);
    const year = date.getFullYear().toString().slice(-2);
    const month = (date.getMonth() + 1);
    const day = date.getDate();
    const hour = date.getHours();
    const min = date.getMinutes();
    const sec = date.getSeconds();
    
    return `${year}-${month}-${day}`;
}
////////////////////

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

  return sortedItems.slice()
})

function readWiki(id){
  router.push({
    path: `/readwiki/${id}`
  })
}

</script>

<template>
<VTable
  density="comfortable"
  fixed-header
  height="300"
>
  <thead>
    <tr>
      <th class="text-center">
        No
      </th>
      <th class="text-center" >
        Title
      </th>
      <th class="text-center">
        Category
      </th>
      <th class="text-center">
        Date
      </th>
    </tr>
  </thead>

  <tbody>
    <tr
      v-for="(item, idx) in rankedItems.slice(0, 20)" :key="idx"
    >
      <td class="text-center" style="width: 50pt">
        {{ idx + 1 }}
      </td>
      <td @click="readWiki(item.id)" style="cursor: pointer; color: #8C57FF;">
        {{ item.title }}
      </td>
      <td class="text-center" style="width: 100pt">
        {{  item.title }}
      </td>
      <td class="text-center" style="width: 150pt">
        {{ formatDate(item.date) }}
      </td>
    </tr>
  </tbody>
</VTable>
</template>
