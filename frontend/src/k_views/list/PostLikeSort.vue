<script setup>
import { onMounted, ref, watch } from 'vue';
import PostLike from '@/k_views/like/PostLike.vue';
import PostCom from '@/k_views/post/PostModal.vue';

const serverAddress = inject('serverAddress')
const auth = inject('auth')

const posts = ref([])
const ispostmodal = ref(false)
const selectedPost = ref([])
const replies = ref([])

async function getPosts() {
  try {
    const response = await fetch(
      `http://${serverAddress}/api/post/read/public`,
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
      alert("실패!")
    }
    const beforePosts = await response.json()

    posts.value = await Promise.all(beforePosts.map(async (post) => {
      const likes = await getLikes(post.id)
      return { ...post, likes: likes }
    }))

    posts.value.sort((a, b) => b.likes - a.likes)
  } catch (error) {
    console.error(error)
    alert("실패!")
  }
}

onMounted(getPosts)

async function getLikes(postId) {
  try {
    const response = await fetch(`http://${serverAddress}/api/postlike/read/${postId}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${auth}`
      },
      credentials: 'include'
    })

    if (!response.ok) {
      alert("실패!")
    }
    const likes = await response.json()
    return likes
  } catch (error) {
    console.error(error)
    alert("실패!")
  }
}

async function checkLike(postId){

  const response = await fetch(
      `http://${serverAddress}/api/postlike/check/${postId}`,
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
    getLikes(postId)
  }
}

async function getReply(postId){

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
    // alert("실패!")
  } else{
    replies.value = await response.json()
  }
}

function openpostmodal(post) {
  ispostmodal.value = !ispostmodal.value
  selectedPost.value = post
  getReply(selectedPost.value.id)
}
function closepostmodal() {
  ispostmodal.value = !ispostmodal.value
  // selectedPost.value = null
}

watch(ispostmodal, (value) => {
  if (value === true) {
    document.documentElement.style.overflow = 'hidden'
  } else {
    document.documentElement.style.overflow = 'auto'
  }
})

</script>

<template>
  <VTable>  
    <thead>
      <tr>
        <th class="text-center">
          Title
        </th>
        <th class="text-uppercase text-center">
          Like
        </th>
      </tr>
    </thead>

    <tbody>
      <tr v-for="(item, index) in posts.slice(0,5)" :key="index">
        <td class="text-center">
          <VIconBtn @click="openpostmodal(item)" style="cursor: pointer;">
            {{ item.title }}
          </VIconBtn>
        </td>
        <td class="text-center">
          <VIconBtn @click="checkLike(item.id)" style="cursor: pointer;">
            <PostLike :postlike="item" />
          </VIconBtn>
        </td>

        <div class="modal-wrap" v-show="ispostmodal" @click="closepostmodal()">
          <div class="modal-container" @click.stop="">
            <div class="modal-body">
              <PostCom :post="selectedPost" :replies="replies" :getLikes="getLikes"/>
            </div>
          </div>
        </div>

      </tr>
    </tbody>
  </VTable>

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