<script setup>
import { useRouter } from 'vue-router';
import { VCardText, VCol, VDivider, VIcon, VRow, VTextField } from 'vuetify/lib/components/index.mjs';
import ReplyVue from '@/k_views/reply/Reply.vue';
import avatar1 from '@images/avatars/avatar-1.png';

const serverAddress = inject('serverAddress')
const auth = inject('auth')

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
const replylike = ref([])
const star = ref([])
const replys = ref([])
const isDetails = ref(false)
const isReplyDetails = ref(false)
const isUpdateReply = ref(false)
const checkReplyId = ref(null)
const ispostoption = ref(false)

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
      `http://${serverAddress}/api/postlike/check/${props.post.id}`,
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${auth}`
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

async function getReplyLikes(replyId){
  
  const response = await fetch(
      `http://${serverAddress}/api/replylike/read/${replyId}`,
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${auth}`
        },
        credentials: 'include'
      }
  )

  if(!response.ok) {
    alert("실패!")
  } else{
    replylike.value[replyId]= await response.json()
  }
}

async function checkStar(){

  const response = await fetch(
      `http://${serverAddress}/api/poststar/check/${props.post.id}`,
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${auth}`
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
      `http://${serverAddress}/api/postlike/read/${props.post.id}`,
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${auth}`
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
      `http://${serverAddress}/api/poststar/read/${props.post.id}`,
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${auth}`
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
        'Authorization': `Bearer ${auth}`
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
      `http://${serverAddress}/api/reply/update/${replyId}`,
      {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
        'Authorization': `Bearer ${auth}`,
        credentials: 'include'
      }
  )

  if(!response.ok) {
    alert("실패!")
  } else{
    getReply(props.post.id);
    editReply.value.content=''
  }
}

async function delPost(postId) {
  if(confirm("삭제하시겠습니까?")) {
    const response = await fetch(
      `http://${serverAddress}/api/post/delete/${postId}`,
      {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json'
        },
        'Authorization': `Bearer ${auth}`,
        credentials: 'include'
      }
    )
    if(!response.ok) {
      alert("삭제 실패")
    } else {
      alert("삭제되었습니다")
      window.location.reload()
    }
  }
}

const router = useRouter();

function gotoupdatepost(postId) {
  router.push({ path: `/updatepost/${postId}`})
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
              <div style="font-weight: 500; font-size: small; display: flex; align-items: center;">
                <span style="margin-right: 10px">
                  <img class="propile-img" :src="avatar1">
                </span>
                <span style="margin-right: 5px;">
                  {{ post.user.username }}
                </span>
                <span> • </span>
                <span style="margin-left: 5px;">
                  {{ formatDate(post.date) }}
                </span>
                <VIconBtn style="margin-left: auto">
                  <VIcon ref="postoptionactivator" icon="ri-more-2-line" style="cursor: pointer;" />
                  <VMenu
                    v-model="ispostoption"
                    :activator="$refs.postoptionactivator"
                    location="bottom end"
                    width="auto"
                    offset="5px"
                    >
                    <VList>
                      <VListItem link>
                        <VListItemTitle @click="gotoupdatepost(post.id)" style="font-size: small;">
                          수정
                        </VListItemTitle>
                      </VListItem>
                      <VListItem link>
                        <VListItemTitle @click="delPost(post.id)" style="font-size: small;">
                          삭제
                        </VListItemTitle>
                      </VListItem>
                    </VList>
                  </VMenu>
                </VIconBtn>
              </div>
            </VCol>
          </div>

          <div class="post-title">
            <VCardTitle style="font-weight: 700;">{{ post.title }}</VCardTitle>
          </div>

          <VDivider />
            <VCol class="post-content, mt-2" style="margin-left: 5px;">
              <div v-html="post.content.length  > 100 ? post.content.slice(0,100) + '...' :post.content"></div>
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
              <VCardText>
                <div v-html="post.content"></div>
              </VCardText>
            </div>
          </VExpandTransition>
          
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
              <!-- <VCardText v-for="(item, index) in replys" :key="index"> -->
              <VCardText>
                <ReplyVue :postob="props.post"></ReplyVue>
                <!-- <VRow >
                  <VCol cols="auto">
                    <VIconBtn @click="toggleReply(item.id)" style="font-size: 10pt; cursor: pointer;">
                      수정
                    </VIconBtn>
                  </VCol>
                  <VCol cols="auto" >
                    <VIconBtn @click="delReply(item.id)" style="font-size: 10pt; cursor: pointer;">
                    삭제
                    </VIconBtn>
                  </VCol>
                </VROW>

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
                </VExpandTransition> -->

              </VCardText>
            </div>
          </VExpandTransition>

        </VCol>
      </VCol>
    </VCardText>
  </VCard>
</template>

<style lang="scss">
// .v-card .trophy {
//   position: absolute;
//   inline-size: 5.188rem;
//   inset-block-end: 1.25rem;
//   inset-inline-end: 1.25rem;
// }
.post-image {
    width: 100px;
    height: 100px;
    border-radius: 50%;
    margin-bottom: 10px;
}
// .post-title {
//     font-weight: bold;
//     margin-bottom: 5px;
// }
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

.propile-img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}

</style>