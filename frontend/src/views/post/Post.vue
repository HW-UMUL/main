<script setup>
import { formatDateToMonthShort } from '@/@core/utils/formatters';
import { VCardItem, VCol, VDivider, VIcon, VTextField } from 'vuetify/lib/components/index.mjs';

// 년-월-일
const formatDate = function(value) {
    const date = new Date(value);
    const year = date.getFullYear();
    const month = (date.getMonth() + 1);
    const day = date.getDate();
    
    return `${year}-${month}-${day}`;
}

const isDetails = ref(false)
const isReplyDetails = ref(false)
//const post = defineProps(['post'])
const props = defineProps({
    post: Object
})

const like = ref([])
const star = ref([])

const reply = ref({
  content: ''
})

const replys = ref([])

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
                  <span class="post-name" style="margin-left:10px; margin-right: auto;">{{ post.user.username }}</span>
                  <span class="post-date" style="margin-left: auto;">{{ formatDate(post.date) }}</span>
                  </span>
                </VCol>
              </div>
            <div class="post-title">
              <VCardTitle>{{ post.title }}</VCardTitle>
            </div>
            <VDivider />
            <VCol>
              <p class="post-content">{{ post.content.length > 100 ? post.content.slice(0, 100) + '...': post.content }}</p>
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
            <VCol class="post-comments">
                <!-- <span class="text-high-emphasis">댓글</span> -->

                <!-- <VCardItem class="reply-con">
                  <p>댓글</p>
                  <VCardText v-for="(item, index) in replys" :key="index">
                      <VDivider />
                      {{ item.content }}
                    </VCardText>
                  <IConBtn @click="$emit('close', false)">close</IConBtn>

                </VCardItem> -->

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
                      <VDivider />
                      {{ item.content }}
                    </VCardText>
                  </div>
                </VExpandTransition>

                <!-- <VCol v-for="(item, index) in replys" :key="index">
                  <VDivider />
                    {{ item.content }}
                </VCol> -->
                <div>
                  <form @submit.prevent="writeReply(post.id)">
                    <VCol
                    >
                      <VTextField
                      input
                      id="reply"
                      v-model="reply.content"
                      placeholder="댓글"
                      label="댓글"
                      />
                    </VCol>
                    
                    <VCol
                    cols="12"
                    md="1">
                      <VBtn
                        
                        type="submit"
                        class="me-2"
                      >
                        작성
                      </VBtn>
                    </VCol>

                  </form>
                </div>
            </VCol>
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