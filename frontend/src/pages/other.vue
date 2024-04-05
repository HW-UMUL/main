<script setup>
import Post from '@/k_views/post/Post.vue'
import OtherWikiList from '@/views/wiki/OtherWikiList.vue'
import { useRouter } from "vue-router"
import avatar1 from '@images/avatars/avatar-1.png';
import { useDisplay } from 'vuetify'
import { onMounted } from 'vue';

const { mdAndUp, mdAndDown } = useDisplay()

const serverAddress = inject('serverAddress')
const profileAddress = inject('profileAddress')

const auth = inject('auth')
const router = useRouter()

const props = defineProps({
    userId: Number
})


const posts = ref([])
const user = ref([])

async function getUser(){

  const response = await fetch(
      `http://${serverAddress}/api/read/user/${props.userId}`,
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
    user.value = await response.json()
  }
}

async function getPosts(){

const response = await fetch(
    `http://${serverAddress}/api/post/read/user/${props.userId}`,
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
  posts.value = await response.json()
}
}


getUser()
getPosts()


onMounted(()=>{
  getUser()
})
/////////////////////////////////////////////////////////

const changePostWiki = ref(true)

</script>

<template>
  <VRow>
    <VCol
      cols="12"
      md="8"
      class="mb-4"
    >
    <VRow>
      <div
      class="table-image"
      >
      <img v-if="!user.profile" class="table-propile-img" :src="avatar1">
      <img v-else class="table-propile-img" :src="profileAddress + user.profile.storeFileName">

    </div>
      <div
      class="table-info"
      >
      <div
      style="
      margin-left: 5%;
      font-size:40px;
      font-weight: 700;
      ">
      {{ user.username }}
      </div>
      <div
      style="
      margin-bottom: 5%;
      ">
      </div>
      <div
      style="
      margin-left: 5%;
      font-size:18px;
      word-wrap: break-word;
      white-space: pre-line;
      ">
      {{ user.email }}
      </div>
      
  </div>
    </VRow>
    <VRow style="height: 30px; margin-bottom: 20px;">
      <div class="selectPostWiki" @click="changePostWiki=true">POST</div>
      <div class="bar"></div>
      <div class="selectPostWiki" @click="changePostWiki=false">WIKI</div>
    </VRow>





      <div v-if="changePostWiki" v-for="(item, index) in posts" :key="index">
        <Post :post="item" style="margin-bottom: 20px;"/>       
      </div>
      <div v-if="!changePostWiki">
        <OtherWikiList v-bind:userId="props.userId"/>
      </div>


    </VCol>  
    <VCol
      cols="12"
      md="4" 
    >    
  
    </VCol>  

  </VRow>

</template>

<style lang="scss">
@use "@core/scss/pages/page-auth.scss";
.selectPostWiki{
  align-items: center;
  width: 49%;
  display: flex;
  justify-content: center;
  font-size: 20px; 
//  font-weight: bold;
}

.bar {
    width: 1px; /* 바의 너비 */
    background-color: gray; /* 바의 배경색 */
}

.table-image{
  width: 30%;
  height:100%;
}
.table-info{
  width: 70%;
}

.table-propile-img {
  margin-left: 5%;
   width: 200px;
   height: 200px;
  border-radius: 50%;
  object-fit: cover;
}

</style>