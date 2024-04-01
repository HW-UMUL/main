<script setup>
import {ref} from 'vue'

const props = defineProps({
    replyitem: Object,
})

const editReply = ref({
  content: ''
})

const replylike = ref([])

async function checkReplyLike(){

    const response = await fetch(
        `http://localhost:8080/api/replylike/check/${props.replyitem.id}`,
        {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        credentials: 'include'
        }
    )

    if(!response.ok) {
    alert("실패!")
    } else{
    getReplyLikes()
    }
}

async function getReplyLikes(){
  
  const response = await fetch(
      `http://localhost:8080/api/replylike/read/${props.replyitem.id}`,
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
        credentials: 'include'
      }
  )

  if(!response.ok) {
    
  } else{
    replylike.value= await response.json()
  }
}

getReplyLikes()

</script>

<template>
  <VCol>
      <VIconBtn @click="checkReplyLike()" style="font-size: small; cursor: pointer">
      추천 {{ replylike }}
      </VIconBtn>
  </VCol>
</template>