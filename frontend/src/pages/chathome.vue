<script setup>
import AnalyticsAward from '@/views/dashboard/AnalyticsAward.vue';
import ChatRoom from '@/views/chat/ChatRoom.vue';

const chatRooms = ref([])

async function getChatRooms(){

  const response = await fetch(
      `http://localhost:8080/api/wiki/read`,
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
    wikis.value = await response.json()
  }
}

async function searchWiki(){
  const response = await fetch(
      `http://localhost:8080/api/wiki/search/${props.keyword}`,
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
    wikis.value = await response.json()
 }
 
}


if(props.keyword == null){
  getWikis()
} 
</script>

<template>
  <VRow>
    <VCol
      cols="12"
      md="8"
      class="mb-4"
    >
    
    <div v-for="(item, index) in wikis" :key="index">
      <!-- {{ item }}       -->
      <wiki :wiki="item" style="margin-bottom: 20px;"/>
    </div>
        <!-- <wiki style="margin-bottom: 20px;"/>
        <wiki style="margin-bottom: 20px;"/>
        <wiki style="margin-bottom: 20px;"/> -->
    </VCol>  
    <VCol
      cols="12"
      md="4"
    >
        <AnalyticsAward />
    </VCol>  

  </VRow>
</template>
