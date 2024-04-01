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

const statistics = [
  {
    title: 'Sales',
    stats: '245k',
    icon: 'ri-pie-chart-2-line',
    color: 'primary',
  },
  {
    title: 'Customers',
    stats: '12.5k',
    icon: 'ri-group-line',
    color: 'success',
  },
  {
    title: 'Product',
    stats: '1.54k',
    icon: 'ri-macbook-line',
    color: 'warning',
  },
  {
    title: 'Revenue',
    stats: '$88k',
    icon: 'ri-money-dollar-circle-line',
    color: 'info',
  },
]

const wiki = reactive([])

axios
  .get(`http://${serverAddress}/api/wiki/read/public`, {
    headers: {
      Authorization: authToken,
    },
  })
  .then(res => {
    wiki.value = res.data
    for (let i = 0; i < res.data.length; i++) {
      getStars(wiki.value[i]?.id)
    }
  })

const likestar = reactive({
  like: [],
  star: [],
})

async function getStars(wikiId) {
  const res = await axios
    .create({
      baseURL: `http://${serverAddress}/api/wikistar/read/${wikiId}`,
      headers: { Authorization: authToken },
      withCredentials: true,
    })
    .get()
  const stars = res.data
  likestar.star[wikiId] = stars
}

const rankedItems = computed(() => {
  if (!wiki.value) return [] // wiki.value가 비어 있는 경우 빈 배열 반환

  // 좋아요 개수를 기준으로 wiki.value를 정렬
  const sortedItems = wiki.value.slice().sort((a, b) => {
    return likestar.star[b.id] - likestar.star[a.id]
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
        <th class="text-uppercase text-center">Star</th>
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
        <td class="text-center">
          {{ likestar.star[item.id] }}
        </td>
      </tr>
    </tbody>
  </VTable>
</template>
