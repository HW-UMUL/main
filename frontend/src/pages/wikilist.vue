<script setup>
import axios from "axios";
import ReadWiki from "/src/pages/readwiki.vue"
import { useRouter } from "vue-router";

const router = useRouter()
const route = useRoute()
const state = reactive({
  items: []
})

axios.get("http://localhost:8080/api/wiki/readall", {
  headers: {
    'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiYXV0aCI6IlJPTEVfQURNSU4sUk9MRV9VU0VSIiwiZXhwIjoxNzk2NTM0MzE2fQ.iAIjL4vyD_s1WXP-Ai4qFGMp1NKHmb6M2BNhYfktN-QyMekNHl0Nvq5E_B-w0leCx5u_q9bztqDh0jywq-NB2g'
  },
}).then((res) => {
    state.items = res;
    console.log(state.items.data)
    console.log(state.items.data.length)
})

const sendId = (index) => {
  router.push({
    name: 'readwiki',
    params: { id: state.items.data[index].id }
  });
};
</script>

<template>
    <VCardTitle>Wiki List</VCardTitle>
    <table class="wikititle" border="1" style="border-collapse: collapse">
        <tr v-for="i in state.items.data?.length" :key="i"><td>
          <button type="button" @click="sendId(i-1)">
            {{ state.items.data[i-1]?.title }}</button>
        </td></tr>
    </table>
</template>
