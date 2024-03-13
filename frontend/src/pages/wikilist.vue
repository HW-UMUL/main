<script setup>
import axios from "axios";
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

const router = useRouter()
const route = useRoute()
const state = reactive({
  items: []
});
axios.get("http://localhost:8080/api/wiki/readall", {
      headers: {
        'Authorization': authToken
      },
}).then((res) => {
  state.items = res;
  console.log(state.items.data[0])
  console.log(state.items.data.length)
})

function sendId(id) {
  router.push({ name: 'readwiki', params: { id } });
}
</script>

<template>
    <VCardTitle>Wiki List</VCardTitle>
    <table class="wikititle" border="1" style="border-collapse: collapse">
        <tr v-for="(item, idx) in state.items.data" :key="idx"><td>
          <button type="button" @click="sendId(item.id)">
             {{ item.title }}</button>
        </td></tr>
    </table>
</template>
