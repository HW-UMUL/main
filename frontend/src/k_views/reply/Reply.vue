<script setup>
import {ref} from 'vue'
import ReplyOption from './ReplyOption.vue';

const serverAddress = inject('serverAddress')
const auth = inject('auth')

const props = defineProps({
    postob: Object
})

const replys = ref([])
const reply = ref({
  content: ''
})

async function getReply(postId){

  const formData = {
    content: reply.value.content
  }

  const response = await fetch(
    `http://${serverAddress}/api/reply/readpost/${postId}`,
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
    replys.value = await response.json()
    reply.value.content = ''
  }
}

async function writeReply(postId){
  console.log(postId)
  const formData = {
    content: reply.value.content
  }

  const response = await fetch(
      `http://${serverAddress}/api/reply/create/${postId}`,
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${auth}`
        },
        body: JSON.stringify(formData),
        credentials: 'include'
      }
  )

  if(!response.ok) {
    alert("실패!")
  } else{
    getReply(postId)
  }
}

getReply(props.postob.id)
</script>

<template>
  <div v-for="(item, index) in replys" :key="index">
    <VDivider class="mb-2"/>
    <div class="mb-2">
      <ReplyOption :getReply="getReply" :reply="item" :post="props.postob" /> 
    </div>
  </div>

  <VRow>
    <VCol>
      <VTextarea
      input
      id="reply"
      v-model="reply.content"
      placeholder="댓글"
      label="댓글"
      style="white-space: pre-line;"
      rows="1"
      >
      </VTextarea>
    </VCol>

    <div>
      <VCol class="mt-1">
        <VBtn @click="writeReply(props.postob.id)"
            type="button"
            class="me-2"
          >
            작성
        </VBtn>
      </VCol>
    </div>
  </VRow>

</template>

<style lang="scss">
.reply-propile-img {
  width: 30px;
  height: 30px;
  border-radius: 50%;
}
</style>