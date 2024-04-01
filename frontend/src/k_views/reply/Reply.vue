<script setup>
import {ref} from 'vue'
import avatar1 from '@images/avatars/avatar-1.png';
import ReplyLike from '../like/ReplyLike.vue';
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

const isUpdateReply = ref(false)
const isSameReplyId = ref(false)
const checkReplyId = ref(null)

// 날짜
const formatDate = function(value) {
  const date = new Date(value);
  const year = date.getFullYear();
  const month = (date.getMonth() + 1);
  const day = date.getDate();
  const hour = date.getHours();
  const min = date.getMinutes();
  const sec = date.getSeconds();
  
  return `${year}-${month}-${day} ${hour}:${min}`;
}

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

const editReply = ref({
  content: ''
})

async function updateReply(replyId){
  const formData = {
    content: editReply.value.content
  }

  const response = await fetch(
      `http://${serverAddress}/api/reply/update/${replyId}`,
      {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${auth}`,
        },
        body: JSON.stringify(formData),
        credentials: 'include'
      }
  )

  if(!response.ok) {
    alert("실패!")
  } else{
    isUpdateReply.value=(false)
    editReply.value.content=''
    getReply(props.postob.id)
  }
}

getReply(props.postob.id)

////////////////////
// function toggleReply() {
//     isUpdateReply.value=!isUpdateReply.value
//     const emit = () => {('update:isUpdateReply', isUpdateReply.value)}
// }

async function delReply(replyId) {
  if(confirm("삭제하시겠습니까?")) {
    const response = await fetch(
      `http://${serverAddress}/api/reply/delete/${replyId}`,
      {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${auth}`,
        },
        credentials: 'include'
      }
    )
    if(!response.ok) {
      alert("삭제 실패")
    } else {
      alert("삭제되었습니다")
      props.getReply(props.post.id);
    }
  }
}

function OptionReply(newvalue) {
  isUpdateReply.value = newvalue
}

function checkReply(newvalue) {
  isSameReplyId.value = newvalue
}
</script>

<template>
  <div v-for="(item, index) in replys" :key="index">
    <VDivider class="mb-2"/>
    <ReplyOption :getReply="getReply" :reply="item" :post="props.postob" /> 
  </div>

  <VRow>
    <VCol>
      <VTextField
      input
      id="reply"
      v-model="reply.content"
      placeholder="댓글"
      label="댓글"
      >
      </VTextField>
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