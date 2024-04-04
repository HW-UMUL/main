<!-- eslint-disable no-restricted-imports -->
<script setup>
import { hexToRgb } from '@layouts/utils'
import axios from 'axios'
import { useTheme } from 'vuetify'
import { useRouter } from 'vue-router'

////////////////////
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

const vuetifyTheme = useTheme()

const series = [
  {
    data: [0, 20, 5, 30, 15, 45],
  },
]

const chartOptions = computed(() => {
  const currentTheme = vuetifyTheme.current.value.colors
  const variableTheme = vuetifyTheme.current.value.variables

  return {
    chart: {
      parentHeightOffset: 0,
      toolbar: { show: false },
    },
    tooltip: { enabled: false },
    grid: {
      borderColor: `rgba(${hexToRgb(String(variableTheme['border-color']))},${variableTheme['border-opacity']})`,
      strokeDashArray: 6,
      xaxis: { lines: { show: true } },
      yaxis: { lines: { show: false } },
      padding: {
        top: -10,
        left: -7,
        right: 5,
        bottom: 5,
      },
    },
    stroke: {
      width: 3,
      lineCap: 'butt',
      curve: 'straight',
    },
    colors: [currentTheme.primary],
    markers: {
      size: 6,
      offsetY: 4,
      offsetX: -2,
      strokeWidth: 3,
      colors: ['transparent'],
      strokeColors: 'transparent',
      discrete: [
        {
          size: 5.5,
          seriesIndex: 0,
          strokeColor: currentTheme.primary,
          fillColor: currentTheme.surface,
          dataPointIndex: series[0].data.length - 1,
        },
      ],
      hover: { size: 7 },
    },
    xaxis: {
      labels: { show: false },
      axisTicks: { show: false },
      axisBorder: { show: false },
    },
    yaxis: { labels: { show: false } },
  }
})

const wiki = reactive([])
const wiki2 = reactive([])

axios
  .get(`http://${serverAddress}/api/wiki/read/public`, {
    headers: {
      Authorization: authToken,
    },
  })
  .then(res => {
    wiki.value = res.data
    for (let i = 0; i < res.data.length; i++) {
      getLikes(wiki.value[i]?.id)
    }
  })


const likestar = reactive({
  like: [],
  star: [],
})

async function getLikes(wikiId) {
  const res = await axios
    .create({
      baseURL: `http://${serverAddress}/api/wikilike/read/${wikiId}`,
      headers: { Authorization: authToken },
      withCredentials: true,
    })
    .get()
  const likes = res.data
  likestar.like[wikiId] = likes
}

const rankedItems = computed(() => {
  if (!wiki.value) return [] // wiki.value가 비어 있는 경우 빈 배열 반환

  // 좋아요 개수를 기준으로 wiki.value를 정렬
  const sortedItems = wiki.value.slice().sort((a, b) => {
    return likestar.like[b.id] - likestar.like[a.id]
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
      <th class="text-center" style="width: 60pt;">
        No
      </th>
      <th class="text-center" >
        Title
      </th>
      <th class="text-center">
        Category
      </th>
      <th class="text-center">
        Like
      </th>
      <!-- <th class="text-center">
        Star
      </th> -->
      <th class="text-center">
        Date
      </th>
    </tr>
  </thead>

  <tbody>
    <tr
      v-for="(item, idx) in rankedItems.slice(0, 20)" :key="idx"
    >
      <td class="text-center" style="width: 60pt">
        {{ idx + 1 }}
      </td>
      <td @click="readWiki(item.id)"
        style="cursor: pointer; color: #8C57FF;">
        {{ item.title }}
      </td>
      <td class="text-center" style="width: 100pt">
        {{  item.title }}
      </td>
      <td class="text-center" style="width: 100pt">
        {{ likestar.like[item.id] }}
      </td>
      <!-- <td class="text-center">
        {{  }}
      </td> -->
      <td class="text-center" style="width: 150pt">
        {{ formatDate(item.date) }}
      </td>
    </tr>
  </tbody>
</VTable>
</template>
