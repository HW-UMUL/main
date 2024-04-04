<script setup>
import { ref } from 'vue';
import PostModal from '@/k_views/post/PostModal.vue';
import App from '@/App.vue';

// 년-월-일
const formatDate = function(value) {
    const date = new Date(value);
    const year = date.getFullYear().toString().slice(-2);
    const month = (date.getMonth() + 1);
    const day = date.getDate();

    return `${year}-${month}-${day}`;
}

const sortDirection = ref('desc')
const sortBy = ref('id')

function togglesort(sortType) {
  if (sortBy.value === sortType) {
    sortDirection.value = sortDirection.value === 'asc' ? 'desc' : 'asc';
  } else {
    sortBy.value = sortType;
    sortDirection.value = 'asc';
  }
  issort();
}

function issort() {
  sortBy.value === 'id';
  sortBy.value === 'like';
  sortBy.value === 'star';
  sortBy.value === 'date';

  sortedPosts.value.sort((a, b) => {
    const compareResult = sortDirection.value === 'asc' ? -1 : 1;
    switch (sortBy.value) {
      case 'id':
        return compareResult * (a.id - b.id);
      case 'like':
        return compareResult * (a.likes - b.likes);
      case 'star':
        return compareResult * (a.stars - b.stars);
      case 'date':
        return compareResult * (new Date(a.date) - new Date(b.date));
      default:
        return 0;
    }
  })
}

const serverAddress = inject('serverAddress')
const auth = inject('auth')

const sortedPosts = ref([])
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
      const stars = await getStars(post.id)
      return { ...post, likes: likes, stars: stars }
    }))

    sortedPosts.value = [...posts.value]; // 정렬된 배열 생성
    issort()
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

async function getStars(postId) {
  
    const response = await fetch(`http://${serverAddress}/api/poststar/read/${postId}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${auth}`
      },
      credentials: 'include'
    })

    if (!response.ok) {
      console.error(error)
    } else{
    const stars = await response.json()
    return stars
  }
}


function openpostmodal(post) {
  ispostmodal.value = !ispostmodal.value
  selectedPost.value = post
  App.emit('PostLikeSortModal', true)
}

function closepostmodal() {
  ispostmodal.value = !ispostmodal.value
}

</script>

<template>
  <div
   style="font-size: small;
   margin: 10px;">
      total : {{ posts.length }}
    </div>
<VTable
density="comfortable"
fixed-header
height="440"
>
  <thead>
    <tr>
      <th class="text-center">
        No
        <VIcon size="12pt"
          :icon="sortBy === 'id' ? (sortDirection === 'asc' ? 'ri-arrow-up-s-line' : 'ri-arrow-down-s-line') : 'ri-subtract-line'"
          @click="togglesort('id')" />
      </th>
      <th class="text-center" >
        Title
      </th>
      <th class="text-center">
        Writer
      </th>
      <th class="text-center">
        Like
        <VIcon
         size="12pt"
         :icon="sortBy === 'like' ? (sortDirection === 'asc' ? 'ri-arrow-up-s-line' : 'ri-arrow-down-s-line') : 'ri-subtract-line'"
         @click="togglesort('like')" />
      </th>
      <th class="text-center">
        Star
        <VIcon
          size="12pt"
          :icon="sortBy === 'star' ? (sortDirection === 'asc' ? 'ri-arrow-up-s-line' : 'ri-arrow-down-s-line') : 'ri-subtract-line'"
          @click="togglesort('star')" />
      </th>
      <th class="text-center">
        Date
        <VIcon
         size="12pt" 
         :icon="sortBy === 'date' ? (sortDirection === 'asc' ? 'ri-arrow-up-s-line' : 'ri-arrow-down-s-line') : 'ri-subtract-line'"
         @click="togglesort('date')" />
      </th>
    </tr>
  </thead>

  <tbody>
    <tr v-for="(item, index) in sortedPosts" :key="index">
      <td class="text-center" style="width: 60pt">
        {{ item.id }}
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
        {{ item.likes }}
      </td>
      <td class="text-center" style="width: 100pt">
        {{ item.stars }}
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