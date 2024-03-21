<script setup>
import axios from 'axios'
import { ref } from 'vue'
import { useRouter } from 'vue-router'

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
console.log('토큰:', authToken)

const route = useRoute()
const router = useRouter()

const wiki = ref(null)

axios
  .get(`http://localhost:8080/api/wiki/read/${route.params.id}`, {
    headers: {
      Authorization: authToken,
    },
  })
  .then(res => {
    wiki.value = res.data
    console.log(wiki.value)
    console.log(wiki.value.user.username)
  })

function sendIdToUpdate(id) {
  router.push({ name: 'updatewiki', params: { id } })
}

function deleteAlert() {
  if (confirm('정말로 삭제하시겠습니까?')) {
    axios
      .delete(`http://localhost:8080/api/wiki/delete/${route.params.id}`, {
        headers: {
          Authorization: authToken,
        },
      })
      .then(res => {
        // 삭제가 성공하면 어떤 동작을 수행할지 정의
        console.log('삭제가 성공적으로 수행되었습니다.')
        window.location.href = `http://localhost:5173/dashboard`
      })
      .catch(error => {
        console.error('삭제 중 에러가 발생했습니다:', error)
      })
  }
}

const props = defineProps({
  wiki: Object,
})

const like = ref([])
const star = ref([])

const likestar = reactive({
  like: [],
  star: [],
})
const userLikes = reactive({})
const userStars = reactive({})

async function checkLike(wikiId) {
  const response = await fetch(`http://localhost:8080/api/wikilike/check/${wikiId}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: authToken,
    },
  })

  if (!response.ok) {
    alert('실패!')
  } else {
    getLikes(wikiId)
    if (userLikes[wikiId]) {
      delete userLikes[wikiId]
    } else {
      userLikes[wikiId] = true
    }
  }
}

async function checkStar(wikiId) {
  const response = await fetch(`http://localhost:8080/api/wikistar/check/${wikiId}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: authToken,
    },
  })

  if (!response.ok) {
    alert('실패!')
  } else {
    getStars(wikiId)
  }
}

let isLiked = 0
async function getLikes(wikiId) {
  const response = await fetch(`http://localhost:8080/api/wikilike/read/${wikiId}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: authToken,
    },
    withCredentials: true,
  })

  if (!response.ok) {
    alert('실패!')
  } else {
    const likes = await response.json()
    likestar.like[wikiId] = likes
  }
}

let isStarred = 0
async function getStars(wikiId) {
  const response = await fetch(`http://localhost:8080/api/wikistar/read/${wikiId}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: authToken,
    },
    withCredentials: true,
  })

  if (!response.ok) {
    alert('실패!')
  } else {
    const stars = await response.json()
    likestar.star[wikiId] = stars
  }
}
</script>

<template>
  <VCardTitle>Wiki List</VCardTitle>
  <!-- <table class="wikititle" border="1" style="border-collapse: collapse" width="50%">
        <div style="margin-left:20px">{{ wiki?.user.username }} &nbsp; {{ wiki?.date.substring(0,10) }}
        {{ wiki?.date.substring(12,19) }} &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
         </div>
    <div align="right" margin-right="20px" float="left">
      <button type="button" v-if="userLikes[wiki?.id] !== undefined" @click="checkLike(`${wiki?.id}`)">
          <font-awesome-icon :icon="['fas', 'thumbs-up']" /> 
        </button>  
        <button type="button" v-else @click="checkLike(`${wiki?.id}`)">
          <font-awesome-icon :icon="['far', 'thumbs-up']" />
        </button>
        {{ likestar.like[wiki?.id] }}
         &nbsp; 

         <button v-if="userStars[wiki?.id] !== undefined" type="button" @click="checkStar(`${wiki?.id}`)">
          <font-awesome-icon :icon="['fas', 'star']" />
        </button>
        <button v-else type="button" @click="checkStar(`${wiki?.id}`)">
          <font-awesome-icon :icon="['far', 'star']" />
        </button>
        {{ likestar.star[wiki?.id] }} &nbsp;
    </div>
    <br/>
    <div>
      <table border="1" width="100%" style="border-collapse: collapse;">
        <tr align="center">
          <td>{{ wiki?.title }}</td>
        </tr>
        <tr align="center" height="500">
          <td>{{ wiki?.content }}</td>
        </tr>
      </table>
    </div>
    </table>' -->

  <div style="display: flex; justify-content: space-between">
    <div style="text-align: left; color: #905dff; margin: 10px">{{ wiki?.tag }}</div>
    <div style="margin: 10px; margin-left: auto">
      <button
        type="button"
        v-if="userLikes[wiki?.id] !== undefined"
        @click="checkLike(`${wiki?.id}`)"
      >
        <font-awesome-icon :icon="['fas', 'thumbs-up']" />
      </button>
      <button
        type="button"
        v-else
        @click="checkLike(`${wiki?.id}`)"
      >
        <font-awesome-icon :icon="['far', 'thumbs-up']" />
      </button>
      {{ likestar.like[wiki?.id] }}
    </div>
    <div style="margin: 10px; float: right">
      <button
        type="button"
        v-if="userStars[wiki?.id] !== undefined"
        @click="checkStar(`${wiki?.id}`)"
      >
        <font-awesome-icon :icon="['fas', 'star']" />
      </button>
      <button
        type="button"
        v-else
        @click="checkStar(`${wiki?.id}`)"
      >
        <font-awesome-icon :icon="['far', 'star']" />
      </button>
      {{ likestar.star[wiki?.id] }}
    </div>
  </div>
  <div
    width="100%"
    style="color: gray"
  >
    <h1>{{ wiki?.title }}</h1>
    <p>
      &nbsp;&nbsp;{{ wiki?.user.username }} &nbsp;
      {{ wiki?.date.substring(0, 10) }}
      {{ wiki?.date.substring(12, 19) }}에 최종 변경
    </p>
  </div>
  <br />
  <div
    v-html="`${wiki?.content}`"
    style="width: 100%; height: 50%"
  />

  <div
    align="center"
    style="flex: auto; float: right"
  >
    <button
      @click="sendIdToUpdate(`${wiki.id}`)"
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
      수정
    </button>

    <button
      @click="deleteAlert()"
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
      삭제
    </button>
  </div>
  <!-- <button v-if="isAuthorized" @click="edit">수정</button> -->
</template>
