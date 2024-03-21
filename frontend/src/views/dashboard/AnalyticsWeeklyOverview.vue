<!-- eslint-disable no-restricted-imports -->
<script setup>
import { hexToRgb } from '@layouts/utils';
import axios from "axios";
import { useTheme } from 'vuetify';

const route = useRoute()

//import { useStore } from 'vuex';
// const store = useStore();
// console.log("auth: ",store.state.authToken)

// 토큰 브라우저에서 받아오기 
let authToken = 'Bearer '
const cookies = document.cookie.split(";");
let jwtToken = '';

for (let i = 0; i < cookies.length; i++) {
  const cookie = cookies[i].trim();
  // 쿠키 이름이 'jwtToken'으로 시작하는 경우
  if (cookie.startsWith('jwtToken=')) {
    // 'jwtToken'의 값만 추출
    jwtToken = cookie.substring('jwtToken='.length);
    break;
  }
}
authToken = authToken + jwtToken
console.log("토큰:", authToken)

const vuetifyTheme = useTheme()

const options = computed(() => {
  const currentTheme = ref(vuetifyTheme.current.value.colors)
  const variableTheme = ref(vuetifyTheme.current.value.variables)
  const disabledColor = `rgba(${ hexToRgb(currentTheme.value['on-surface']) },${ variableTheme.value['disabled-opacity'] })`
  const borderColor = `rgba(${ hexToRgb(String(variableTheme.value['border-color'])) },${ variableTheme.value['border-opacity'] })`
  
  return {
    chart: {
      offsetY: -10,
      offsetX: -15,
      parentHeightOffset: 0,
      toolbar: { show: false },
    },
    plotOptions: {
      bar: {
        borderRadius: 6,
        distributed: true,
        columnWidth: '30%',
      },
    },
    stroke: {
      width: 2,
      colors: [currentTheme.value.surface],
    },
    legend: { show: false },
    grid: {
      borderColor,
      strokeDashArray: 7,
      xaxis: { lines: { show: false } },
    },
    dataLabels: { enabled: false },
    colors: [
      currentTheme.value['track-bg'],
      currentTheme.value['track-bg'],
      currentTheme.value['track-bg'],
      'rgba(var(--v-theme-primary),1)',
      currentTheme.value['track-bg'],
      currentTheme.value['track-bg'],
    ],
    states: {
      hover: { filter: { type: 'none' } },
      active: { filter: { type: 'none' } },
    },
    xaxis: {
      categories: [
        'Sun',
        'Mon',
        'Tue',
        'Wed',
        'Thu',
        'Fri',
        'Sat',
      ],
      tickPlacement: 'on',
      labels: { show: false },
      crosshairs: { opacity: 0 },
      axisTicks: { show: false },
      axisBorder: { show: false },
    },
    yaxis: {
      show: true,
      tickAmount: 4,
      labels: {
        style: {
          colors: disabledColor,
          fontSize: '13px',
        },
        formatter: value => `${ value > 999 ? `${ (value / 1000).toFixed(0) }` : value }k`,
      },
    },
    responsive: [
      {
        breakpoint: 1560,
        options: { plotOptions: { bar: { columnWidth: '35%' } } },
      },
      {
        breakpoint: 1380,
        options: { plotOptions: { bar: { columnWidth: '45%' } } },
      },
    ],
  }
})

const series = [{
  data: [
    37,
    57,
    45,
    75,
    57,
    40,
    65,
  ],
}]

const state = reactive({
  items: []
})
axios.get("http://localhost:8080/api/wiki/readall", {
  headers: {
    'Authorization': authToken
  }
}).then((res) => {
    console.log(res.data[0])
    state.items = res;
})

const props = defineProps({
    wiki: Object
})


const likestar = reactive({
  like: [],
  star: [],
})
const userLikes = reactive({});
const userStars = reactive({});

async function checkLike(wikiId){
  const response = await fetch(
      `http://localhost:8080/api/wikilike/check/${wikiId}`,
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization' : authToken
        }
      }
  )

  if(!response.ok) {
    alert("실패!")
  } else{
    getLikes(wikiId)
    if (userLikes[wikiId]) {
      delete userLikes[wikiId];
    } else {
      userLikes[wikiId] = true;
    }
  }

}

async function checkStar(wikiId){

  const response = await fetch(
      `http://localhost:8080/api/wikistar/check/${wikiId}`,
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization' : authToken
        },
      }
    )

    if(!response.ok) {
      alert("실패!")
    } else{
      getStars(wikiId)
      if (userStars[wikiId]) {
        delete userStars[wikiId];
      } else {
        userStars[wikiId] = true;
      }
    }
}

async function getLikes(wikiId){

  const response = await fetch(
      `http://localhost:8080/api/wikilike/read/${wikiId}`,
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization' : authToken
        },
        withCredentials: true,
      }
      
  )

  if(!response.ok) {
    alert("실패!")
  } else{
    const likes = await response.json()
    likestar.like[wikiId] = likes;
  }
}

async function getStars(wikiId){

const response = await fetch(
    `http://localhost:8080/api/wikistar/read/${wikiId}`,
    {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization' : authToken
      },
      withCredentials: true,
    }
  )

  if(!response.ok) {
    alert("실패!")
  } else{
    const stars = await response.json()
    likestar.star[wikiId] = stars;
  }
}

// function checkLike(wikiId) {
//   console.log("check:", authToken)
//   axios.post(`http://localhost:8080/api/wiki/check/${wikiId}`, {
//     headers: {
//       'Authorization' : authToken
//     },
//   }).then(res => {
//     console.log(res)
//   }).catch((error) => {
//     console.log(error)
//   })
// }


</script>

<style>
    .button {
      position: absolute;
      top:50%;
      background-color: lightgray;
    }
</style>
<template>
  <VCol v-for="i in state.items.data?.length" :key="i">
    <hr/>
    <div style="display: flex; justify-content: space-between">
          <div style="text-align: left; color: #905DFF; margin: 10px">{{ state.items.data[i-1]?.tag }}</div>
          <div style="margin: 10px; margin-left: auto">
            <button type="button" v-if="userLikes[state.items.data[i-1].id] !== undefined" @click="checkLike(`${state.items.data[i-1].id}`)">
              <font-awesome-icon :icon="['fas', 'thumbs-up']" />
            </button>  
            <button type="button" v-else @click="checkLike(`${state.items.data[i-1].id}`)">
              <font-awesome-icon :icon="['far', 'thumbs-up']" />
            </button>
            {{ likestar.like[state.items.data[i-1].id] }}
          </div>
        <div style="margin: 10px; float: right;">
          <button type="button" v-if="userStars[state.items.data[i-1].id] !== undefined" @click="checkStar(`${state.items.data[i-1].id}`)">
            <font-awesome-icon :icon="['fas', 'star']" />
          </button>
          <button type="button" v-else @click="checkStar(`${state.items.data[i-1].id}`)">
            <font-awesome-icon :icon="['far', 'star']" />
          </button>
          {{ likestar.star[state.items.data[i-1].id] }}
        </div>
      </div>
    <div width="100%" style="color: gray">
      <h1>{{ state.items.data[i-1]?.title }}</h1>
      <p>&nbsp;&nbsp;{{ state.items.data[i-1]?.user.username }} &nbsp;
         {{ state.items.data[i-1]?.date.substring(0,10) }}
        {{ state.items.data[i-1]?.date.substring(12,19) }}에 최종 변경
      </p>
    </div>
    <br/>
    <div v-html="`${ state.items.data[i-1]?.content }`"/>

    <br/><br/><br/><br/>
</VCol>
</template>
