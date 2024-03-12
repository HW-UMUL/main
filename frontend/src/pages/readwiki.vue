<script setup>
import axios from "axios";
import { ref } from "vue";

const route = useRoute()
const id = 2;
const state = reactive({
  items: []
})

axios.get(`http://localhost:8080/api/wiki/read/${id}`, {
    headers: {
        'Authorization':'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiYXV0aCI6IlJPTEVfQURNSU4sUk9MRV9VU0VSIiwiZXhwIjoxNzk2NTM0MzE2fQ.iAIjL4vyD_s1WXP-Ai4qFGMp1NKHmb6M2BNhYfktN-QyMekNHl0Nvq5E_B-w0leCx5u_q9bztqDh0jywq-NB2g'
    },
}).then((res) => {
    console.log(res.data)
    state.items = res;
})

</script>

<template>
    <VCardTitle>Wiki List</VCardTitle>
    <table class="wikititle" border="1" style="border-collapse: collapse" width="50%">
        <div style="margin-left:20px">{{ state.items.data?.user.username }} &nbsp; {{ state.items.data?.date.substring(0,10) }}
        {{ state.items.data?.date.substring(12,19) }}</div>
    <div align="right" margin-right="20px" float="left">
      <button style="background-color: #905DFF; 
        border: none;
        color: white;
        padding: 7px 15px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        border-style: solid;
        font-size: 15px;" type="button">like</button> &nbsp; 

      <button style="background-color: #905DFF; 
        border: none;
        color: white;
        padding: 7px 15px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        border-style: solid;
        font-size: 15px;" type="button">star</button>
    </div>
    <br/>
    <div>
      <table border="1" width="100%">
        <tr align="center">
          <td>{{ state.items.data?.title }}</td>
        </tr>
        <tr align="center" height="500">
          <td>{{ state.items.data?.content }}</td>
        </tr>
      </table>
    </div>
    </table>
</template>
