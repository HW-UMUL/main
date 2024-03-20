<script setup>
import { formatDateToMonthShort } from '@/@core/utils/formatters';
import { useRouter } from 'vue-router';
import {ref} from 'vue';
import { VCardItem, VCol, VDivider, VIcon, VRow, VTextField } from 'vuetify/lib/components/index.mjs';

// 날짜
const formatDate = function(value) {
    const date = new Date(value);
    const year = date.getFullYear();
    const month = (date.getMonth() + 1);
    const day = date.getDate();
    const hour = date.getHours();
    const min = date.getMinutes();
    const sec = date.getSeconds();
    
    return `${year}-${month}-${day} ${hour}:${min}:${sec}`;
}

const like = ref([])
const star = ref([])
const replys = ref([])
const isDetails = ref(false)
const isReplyDetails = ref(false)
const isUpdateReply = ref(false)
const checkReplyId = ref(null)

const props = defineProps({
    post: Object
})
const reply = ref({
  content: ''
})
const editReply = ref({
  content: ''
})

async function checkLike(){

  const response = await fetch(
      `http://localhost:8080/api/postlike/check/${props.post.id}`,
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
    getLikes()
  }
}

async function checkStar(){

  const response = await fetch(
      `http://localhost:8080/api/poststar/check/${props.post.id}`,
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
    getStars()
  }
}



async function getLikes(){
  
  const response = await fetch(
      `http://localhost:8080/api/postlike/read/${props.post.id}`,
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
    like.value = await response.json()
  }
}

async function getStars(){

  const response = await fetch(
      `http://localhost:8080/api/poststar/read/${props.post.id}`,
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
    star.value = await response.json()
  }
}

async function writeReply(postId){
  const formData = {
    content: reply.value.content
  }

  const response = await fetch(
      `http://localhost:8080/api/reply/create/${postId}`,
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
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


async function getReply(postId){

const formData = {
  content: reply.value.content
}

const response = await fetch(
  `http://localhost:8080/api/reply/readpost/${postId}`,
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
    replys.value = await response.json()
    reply.value.content = ''
  }
}

async function toggleReply(replyId) {
  if(checkReplyId.value != replyId) {
    isUpdateReply.value = true
    checkReplyId.value = replyId;
  }
  else {
    isUpdateReply.value = !isUpdateReply.value;
    checkReplyId.value = replyId;
  }
}

async function updateReply(replyId){
  const formData = {
    content: editReply.value.content
  }

  const response = await fetch(
      `http://localhost:8080/api/reply/update/${replyId}`,
      {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
        credentials: 'include'
      }
  )

  if(!response.ok) {
    alert("실패!")
  } else{
    getReply(props.post.id);
  }
}

async function delPost(postId) {
  if(confirm("삭제하시겠습니까?")) {
    const response = await fetch(
      `http://localhost:8080/api/post/delete/${postId}`,
      {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json'
        },
        credentials: 'include'
      }
    )
    if(response.ok) {
      alert("삭제되었습니다")
      window.location.reload();
    } else {
      alert("삭제 실패")
    }
  }
}

async function delReply(replyId) {
  if(confirm("삭제하시겠습니까?")) {
    const response = await fetch(
      `http://localhost:8080/api/reply/delete/${replyId}`,
      {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json'
        },
        credentials: 'include'
      }
    )
    if(response.ok) {
      alert("삭제되었습니다")
    } else {
      alert("삭제 실패")
    }
    getReply();
  }
}

const router = useRouter();

async function gotoupdatepost(postId) {
  await router.push({ path: `/updatepost`, query: {postId: postId}})
}

getLikes()
getStars()
getReply(props.post.id)

</script>

<template>
  <VCard class="position-relative">
    <VCardText>
      <VCol class="mb-2">
        <VCol class="post-details">
            <div class="post-header">
                <VCol class="post-header" >
                  <span class="circle"></span>
                  <span style="display: flex; justify-content: space-between;">
                  <span class="post-name" style="margin-left:10px; margin-right: auto; font-weight: 600;">{{ post.user.username }}
                  </span>
                  <span class="post-date" style="margin-left: auto;">{{ formatDate(post.date) }}</span>
                  </span>
                </VCol>
              </div>
            <VCol class="post-title">
              <VCardTitle style="font-weight: 700;">{{ post.title }}</VCardTitle>
            </VCol>
            <VDivider />
            <VCol>
              <VCol class="post-content">{{ post.content.length > 100 ? post.content.slice(0, 100) + '...': post.content }}
              </VCol>
            </VCol>
            <VCardActions>
              <VBtn @click="isDetails = !isDetails">
                Details
              </VBtn>

              <VSpacer />

              <VBtn
                icon
                size="small"
                @click="isDetails = !isDetails"
              >
                <VIcon :icon="isDetails ? 'ri-arrow-up-s-line' : 'ri-arrow-down-s-line'" />
              </VBtn>
            </VCardActions>

            <VExpandTransition>
              <div v-show="isDetails">
                <VDivider />
                <VCardText>
                  {{ post.content }}
                </VCardText>
              </div>
            </VExpandTransition>
            
            <VDivider />
            <VCol class="post-interactions">
              <IConBtn @click="checkLike"> 
                <VIcon icon="ri-heart-line"/>
              </IConBtn>
              <span class="post-interactions-item" style="margin-left: 10px; margin-right: 10px;">{{ like }}</span>

              <IConBtn @click="checkStar"> 
                <VIcon icon="ri-star-line"/>
              </IConBtn>
              <span class="post-interactions-item" style="margin-left: 10px; margin-right: 10px;">{{ star }}</span>
            </VCol>

            <VDivider />
                <VCardActions>
                  <VBtn @click="isReplyDetails = !isReplyDetails">
                    Reply
                  </VBtn>
                  <VSpacer />
                  <VBtn
                    icon
                    size="small"
                    @click="isReplyDetails = !isReplyDetails"
                  >
                    <VIcon :icon="isReplyDetails ? 'ri-arrow-up-s-line' : 'ri-arrow-down-s-line'" />
                  </VBtn>
                </VCardActions>

                <VExpandTransition>
                  <div v-show="isReplyDetails">
                    <VCardText v-for="(item, index) in replys" :key="index">
                      <VDivider class="mb-2"/>
                      <div class="mb-4" style="display: flex; justify-content: space-between;">
                        <span style="font-weight: 600;"> {{ item.user.username }} </span>
                        <span> {{ formatDate(item.date) }}</span>
                      </div>
                      <div class="mb-2"> {{ item.content }}</div>

                      <VRow class="justify-end">
                        <VCol cols="auto">
                          <VIconBtn @click="toggleReply(item.id)" style="font-size: 10pt; cursor: pointer;">
                            수정
                          </VIconBtn>
                        </VCol>
                        <VCol cols="auto">
                          <VIconBtn @click="delReply(item.id)" style="font-size: 10pt; cursor: pointer;">
                            삭제
                          </VIconBtn>
                        </VCol>
                      </VRow>
                      
                      <VExpandTransition>
                        <div v-show="isUpdateReply">
                          <div v-if="checkReplyId === item.id">
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
                                  <VBtn @click="updateReply(item.id)"
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
                    </VCardText>
                  </div>
                </VExpandTransition>

                <div>
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
                        <VBtn @click="writeReply(post.id)"
                            type="button"
                            class="me-2"
                          >
                            작성
                        </VBtn>
                      </VCol>
                    </div>
                  </VRow>
                  
                  <VRow>
                    <VCol style="display: flex; justify-content: space-between;">
                      <VBtn @click="gotoupdatepost(post.id)"
                        type="button"
                        style="margin-left: auto;"
                        >
                        수정
                      </VBtn>
                      
                      <VBtn @click="delPost(post.id)"
                        type="button"
                        style="margin-left: 10px;"
                        >
                        삭제
                      </VBtn>
                    </VCol>
                  </VRow>

                </div>
          </VCol>
      </VCol>
    </VCardText>
  </VCard>
</template>

<style lang="scss">
.v-card .trophy {
  position: absolute;
  inline-size: 5.188rem;
  inset-block-end: 1.25rem;
  inset-inline-end: 1.25rem;
}
.post-image {
    width: 100px;
    height: 100px;
    border-radius: 50%; /* 원형 이미지를 위한 border-radius */
    margin-bottom: 10px;
}
// .post-title {
//     font-weight: bold;
//     margin-bottom: 5px;
// }
.post-date {
    color: #666;
    margin-bottom: 10px;
}
.post-content {
    margin-bottom: 10px;
}
.post-interactions {
    display: flex;
    width: 100%;
    margin-bottom: 10px;
}
.post-interactions-item {
    color: #666;
}
.post-comments {
    color: #333;
}

.circle {
    width: 20px;
    height: 20px;
    background-color: red;
    border-radius: 50%; /* 원 모양의 동그라미를 만들기 위해 반지름을 50%로 설정합니다. */

    margin-bottom: 5px;

    display: inline-block;
}

</style>