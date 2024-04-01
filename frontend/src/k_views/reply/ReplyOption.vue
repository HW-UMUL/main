<script setup>
import ReplyLike from '../like/ReplyLike.vue';

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

const serverAddress = inject('serverAddress')
const auth = inject('auth')

const props = defineProps({
    reply:Object,
    post:Object,
    getReply:Function,
})

// const emit = defineEmits()

const isreplyoption=ref(false)
const isUpdateReply = ref(false)
const checkReplyId = ref(null)
const isSameReplyId =ref(false)

function toggleReply() {
    // checkReplyId.value = replyId
    isUpdateReply.value=!isUpdateReply.value
    // emit('update:isUpdateReply', !isUpdateReply.value)
    // emit('update:isSameReplyId', isSameReplyId.value)
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
    props.getReply(props.post.id)
  }
}

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

props.getReply(props.post.id)
</script>
<template>
      <div class="mb-2" style="display: flex; align-items: center;
        font-size: small; color: #999;">
        <span style="margin-right: 10px">
          <img class="reply-propile-img" :src="avatar1">
        </span>
        <span style="margin-right: 5px" >
          {{ props.reply.user.username }}
        </span>
        <span> • </span>
        <span style="margin-left: 5px">
          {{ formatDate(props.reply.date) }}
        </span>

        <VIconBtn style="margin-left: auto">        
          <VIcon ref="replyoption" icon="ri-more-2-line" style="cursor: pointer;" />
          <VMenu
            v-model="isreplyoption"
            :activator="$refs.replyoption"
            width="auto"
            >
            <VList>
              <VListItem link>
                <VListItemTitle @click="toggleReply()" style="font-size: small;">
                  수정
                </VListItemTitle>
              </VListItem>
              <VListItem link>
                <VListItemTitle @click="delReply(props.reply.id)" style="font-size: small;">
                  삭제
                </VListItemTitle>
              </VListItem>
            </VList>
          </VMenu>  
        </VIconBtn>

      </div>
      <div class="mb-2">
        {{ props.reply.content }}
      </div>

      <VRow class="justify-end">
        <ReplyLike :replyitem="props.reply"></ReplyLike>
      </VRow>

        <VExpandTransition>
          <div v-show="isUpdateReply">
            <div >
              <VRow>
                <VCol>
                  <VTextField
                  input
                  id="updatereply"
                  v-model="editReply.content"
                  placeholder="수정"
                  label="수정"
                  >
                  </VTextField>
                </VCol>

                <div>
                  <VCol class="mt-1">
                    <VBtn @click="updateReply(props.reply.id)"
                        type="button"
                        class="me-2"
                      >
                        수정
                    </VBtn>
                  </VCol>
                </div>

              </VRow>
            </div>
          </div>
        </VExpandTransition>
</template>