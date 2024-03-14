<script setup>
import axios from "axios";
import { ref } from "vue";
import { useRouter } from "vue-router";

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

const route = useRoute()
const router = useRouter()

const wiki = ref(null);

axios.get(`http://localhost:8080/api/wiki/read/${route.params.id}`, {
    headers: {
        'Authorization': authToken
    },
}).then((res) => {
    wiki.value = res.data;
    console.log(wiki.value)
    console.log(wiki.value.user.username)
})

function sendIdToUpdate(id) {
  router.push({ name: 'updatewiki', params: { id } });
}

function deleteAlert() {
  if (confirm("정말로 삭제하시겠습니까?")) {
    axios.delete(`http://localhost:8080/api/wiki/delete/${route.params.id}`, {
      headers: {
        'Authorization' : authToken
      },
    }).then((res) => {
        // 삭제가 성공하면 어떤 동작을 수행할지 정의
      console.log("삭제가 성공적으로 수행되었습니다.");
      window.location.href = `http://localhost:5173/dashboard`
    }).catch((error) => {
      console.error("삭제 중 에러가 발생했습니다:", error);
    });
  }
}

const props = defineProps({
    wiki: Object
})


const like = ref([])
const star = ref([])

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
    }
}

let isLiked = 0;
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
    like.value = await response.json()
    isLiked = like.value
    console.log(isLiked)
  }
}

let isStarred = 0;
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
    star.value = await response.json()
    isStarred = star.value;
    console.log(isStarred)
  }
}

function isAuthorized() {

}

</script>

<template>
    <VCardTitle>Wiki List</VCardTitle>
    <table class="wikititle" border="1" style="border-collapse: collapse" width="50%">
        <div style="margin-left:20px">{{ wiki?.user.username }} &nbsp; {{ wiki?.date.substring(0,10) }}
        {{ wiki?.date.substring(12,19) }} &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
        <span @click="checkLike" class="post-interactions-item">좋아요 : {{ like }}</span>  &nbsp;
        <span @click="checkStar" class="post-interactions-item">스크랩 횟수 : {{ star }}</span>  
        </div>
    <div align="right" margin-right="20px" float="left">
      <button type="button" v-if="isLiked == 1" @click="checkLike(`${wiki?.id}`)">
          <font-awesome-icon :icon="['fas', 'thumbs-up']" />
        </button>  
        <button type="button" v-else @click="checkLike(`${wiki?.id}`)">
          <font-awesome-icon :icon="['far', 'thumbs-up']" />
        </button>
         &nbsp; 

         <button v-if="isStarred == 1" type="button" @click="checkStar(`${wiki?.id}`)">
          <font-awesome-icon :icon="['fas', 'star']" />
        </button>
        <button v-else type="button" @click="checkStar(`${wiki?.id}`)" style="padding-right: 10px">
          <font-awesome-icon :icon="['far', 'star']" />
        </button>
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
    </table>'
    <div align="center"><button v-if="isAuthorized" @click="sendIdToUpdate(`${wiki.id}`)"
      style="background-color: #905DFF; 
        border: none;
        color: white;
        padding: 7px 15px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        border-style: solid;
        font-size: 15px;">수정</button>

  <button v-if="isAuthorized" @click="deleteAlert()"
      style="background-color: #905DFF; 
        border: none;
        color: white;
        padding: 7px 15px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        border-style: solid;
        font-size: 15px;">삭제</button></div>
    <!-- <button v-if="isAuthorized" @click="edit">수정</button> -->
</template>
