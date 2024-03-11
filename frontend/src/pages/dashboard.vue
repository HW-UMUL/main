<script setup>
import AnalyticsAward from '@/views/dashboard/AnalyticsAward.vue';
import Post from '@/views/post/Post.vue';

// import post from '@/views/'
import { watchEffect } from 'vue';


const props = defineProps({
    keyword: String
})

const posts = ref([])

watchEffect(() => {
  // props를 감시하고, 변경될 때마다 실행되는 코드
  console.log('Keyword has been updated:', props.keyword);
  if(props.keyword == null){
    getPosts()
  } else{
    searchPost()
  }
});


async function getPosts(){

  const response = await fetch(
      `http://localhost:8080/api/post/read`,
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
        credentials: 'include'
      }
  )

  if(!response.ok) {
    alert("실패!")
  } else{
    posts.value = await response.json()
  }
}

async function searchPost(){
  const response = await fetch(
      `http://localhost:8080/api/post/search/${props.keyword}`,
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
        credentials: 'include'
      }
  )

  if(!response.ok) {
    alert("실패!")
  } else{
    posts.value = await response.json()
 }
 
}


if(props.keyword == null){
  getPosts()
} 
</script>

<template>
  <VRow>
    <VCol
      cols="12"
      md="8"
      class="mb-4"
    >
    
    <div v-for="(item, index) in posts" :key="index">
      <!-- {{ item }}       -->
      <Post :post="item" style="margin-bottom: 20px;"/>
    </div>
        <!-- <Post style="margin-bottom: 20px;"/>
        <Post style="margin-bottom: 20px;"/>
        <Post style="margin-bottom: 20px;"/> -->
    </VCol>  
    <VCol
      cols="12"
      md="4"
    >
        <AnalyticsAward />
    </VCol>  

  </VRow>
</template>
