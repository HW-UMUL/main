<script setup>
import { ref, watch } from 'vue';
import PostLike from '@/k_views/like/PostLike.vue';
import PostModal from '@/k_views/post/PostModal.vue';
import App from '@/App.vue';
import PostStar from '@/k_views/star/PostStar.vue';

// 년-월-일
const formatDate = function(value) {
    const date = new Date(value);
    const year = date.getFullYear().toString().slice(-2);
    const month = (date.getMonth() + 1);
    const day = date.getDate();
    const hour = date.getHours();
    const min = date.getMinutes();
    const sec = date.getSeconds();
    
    return `${year}-${month}-${day}`;
}

const serverAddress = inject('serverAddress')
const auth = inject('auth')

const posts = ref([])
const selectedPost = ref([])
const ispostmodal = ref(false)

async function getPosts() {
  try {
    const response = await fetch(
      `http://${serverAddress}/api/post/read`,
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${auth}`
        },
        credentials: 'include'
      }
    );
    if (!response.ok) {
      console.error(error)
    }
    const beforePosts = await response.json()
    
    posts.value = await Promise.all(beforePosts.map(async (post) => {
      const likes = await getLikes(post.id)
      return { ...post, likes: likes }
    }))

    posts.value.sort((a, b) => b.likes - a.likes)

  } catch (error) {
    console.error(error)
  }
}

getPosts()

async function getLikes(postId){
  
  const response = await fetch(
    `http://localhost:8080/api/postlike/read/${postId}`,
    {
        method: 'GET',
        headers: {
        'Content-Type': 'application/json',
        },
        credentials: 'include'
    }
  )

  if(!response.ok) {
    console.error(error)
  } else{
    const likes = await response.json()
    return likes
  }
}

// async function checkLike(postId){

//   const response = await fetch(
//       `http://${serverAddress}/api/postlike/check/${postId}`,
//       {
//         method: 'POST',
//         headers: {
//           'Content-Type': 'application/json',
//           'Authorization': `Bearer ${auth}`
//         },
//         credentials: 'include'
//       }
//   )

//   if(!response.ok) {
//     console.error(error)
//   } else{
//     // getLikes(postId)
//   }
// }

function openpostmodal(post) {
  ispostmodal.value = !ispostmodal.value
  selectedPost.value = post
  App.emit('PostLikeSortModal', true)
}

function closepostmodal() {
  ispostmodal.value = !ispostmodal.value
  console.log(ispostmodal.value)
}

// watch(ispostmodal, (value) => {
//   if (value === true) {
//     document.documentElement.style.overflow = 'hidden'
//   } else {
//     document.documentElement.style.overflow = 'auto'
//   }
// })

</script>

<template>
<VTable
density="comfortable"
fixed-header
height="300"
>
  <thead>
    <tr>
      <th class="text-center">
        No
      </th>
      <th class="text-center" >
        Title
      </th>
      <th class="text-center">
        Writer
      </th>
      <th class="text-center">
        Like
      </th>
      <th class="text-center">
        Star
      </th>
      <th class="text-center">
        Date
      </th>
    </tr>
  </thead>

  <tbody>
    <tr v-for="(item, index) in posts.slice(0,20)" :key="index">
      <td class="text-center">
        {{ index+1 }}
      </td>
      <td>
        <VIconBtn 
          class="text-center"
          @click="openpostmodal(item)"
          style="cursor: pointer; color: #8C57FF;">
          {{ item.title }}
        </VIconBtn>
      </td>
      <td class="text-center" style="width: 100pt">
        {{ item.user.username }}
      </td>
      <td class="text-center" style="width: 100pt">
        <PostLike :postlike="item" />
      </td>
      <td class="text-center" style="width: 100pt">
        <PostStar :poststar="item" />
      </td>
      <td class="text-center" style="width: 150pt">
      {{ formatDate(item.date) }}
      </td>
    </tr>
  </tbody>
</VTable>

<div class="modal-wrap" v-if="ispostmodal" @click="closepostmodal">
  <div class="modal-container" @click.stop="">
    <div class="modal-body" v-if="selectedPost">
      <PostModal :post="selectedPost" />
    </div>
  </div>
</div>
</template>

<style lang="scss">
/* dimmed */
.modal-wrap {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.2);
  z-index: 100;
}
/* modal or popup */
.modal-container {
  position: relative;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 550px;
  // background: #fff;
  border-radius: 10px;
  padding: 20px;
  box-sizing: border-box;
}
.modal-body {
  max-height: calc(100vh - 200px);
  overflow-y: auto;
}
// html {
//   -ms-overflow-style: none; /* IE and Edge */
//   scrollbar-width: none; /* Firefox */
// }
// html::-webkit-scrollbar {
//   display: none; /* Chrome, Safari, Opera*/
// }
</style>