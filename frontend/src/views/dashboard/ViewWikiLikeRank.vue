<!-- eslint-disable no-restricted-imports -->
<script setup>
import { hexToRgb } from '@layouts/utils'
import axios from 'axios'
import { useTheme } from 'vuetify'

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

axios
  .get(`http://localhost:8080/api/wiki/readall`, {
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
      baseURL: `http://localhost:8080/api/wikilike/read/${wikiId}`,
      headers: { Authorization: authToken },
      withCredentials: true,
    })
    .get()
  const likes = res.data
  likestar.like[wikiId] = likes
}

async function getStars(wikiId) {
  const res = await axios
    .create({
      baseURL: `http://localhost:8080/api/wikistar/read/${wikiId}`,
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
    return likestar.like[b.id] - likestar.like[a.id]
  })

  return sortedItems.slice(0, 5)
})
</script>

<template>
  <VCard>
    <VCardTitle class="text-center">Wiki 좋아요 순위</VCardTitle>
    <VCardText style="margin-top: 15px">
      <div style="display: flex">
        <span style="margin-right: 75px">순위</span>
        <span style="margin-right: 45px">제목</span>
        <span>좋아요 수</span>
      </div>
      <div
        style="justify-content: space-between; display: flex; margin: 5px"
        v-for="(item, idx) in rankedItems"
      >
        <div>{{ idx + 1 }}</div>
        <div>
          <a :href="`http://localhost:5173/readwiki/${item.id}`">{{ item.title }}</a>
        </div>
        <div>{{ likestar.like[item.id] }}</div>
      </div>
    </VCardText>
  </VCard>
</template>
