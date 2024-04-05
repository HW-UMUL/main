<script setup>
import { watch } from 'vue';
import { VCardItem, VCol, VDivider, VIcon, VTextField } from 'vuetify/lib/components/index.mjs';

const serverAddress = inject('serverAddress')
const auth = inject('auth')

const props = defineProps({
  chatRoom: Object
})

const chat = ref([])

async function getRecentMessage(){
  const response = await fetch(
    `http://${serverAddress}/api/chat/read/recent/${props.chatRoom.id}`,
    {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${auth}`,          
      },
      credentials: 'include'
    }
  )

  if(!response.ok) {
    alert("실패!")
  } else{
      try{
        chat.value = await response.json()    
      }
      catch(error) {

      }
  }
}

getRecentMessage()
</script>

<template>
  <VCard rounded="pill">
    <VCardTitle>
      {{ chatRoom.name }}

    </VCardTitle>
    <VCardText v-if="chat.content"> 
      <span>{{ chat.user.username }} : </span>
      <span>{{ chat.content }}</span>      
    </VCardText>
    <VCardText v-if="!chat.content"> 
      <span> Created New Chatting Room</span>
    </VCardText>

  </VCard>
</template>
