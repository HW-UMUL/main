<script setup>
import PostModal from '@/k_views/post/PostModal.vue';

const serverAddress = inject('serverAddress')
const auth = inject('auth')

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

const posts = ref([])

async function getPosts(){

  const response = await fetch(
      `http://${serverAddress}/api/post/read/public`,
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
        credentials: 'include',
        'Authorization': `Bearer ${auth}`
      }
  )

  if(!response.ok) {
    console.error(error)
  } else{
    posts.value = await response.json()
  }
}

getPosts()

function sortedDate(value) {
  const sortedValue = value.slice().sort(function(a,b) {
    return new Date(b.date).getTime() - new Date(a.date).getTime()
  })
  return sortedValue
}

const selectedPost = ref([])
const ispostmodal = ref(false)

function openpostmodal(post) {
  ispostmodal.value = !ispostmodal.value
  selectedPost.value = post
}

function closepostmodal() {
  ispostmodal.value = !ispostmodal.value
  console.log(ispostmodal.value)
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
          Date
        </th>
      </tr>
    </thead>

    <tbody>
      <tr
        v-for="(item, index) in sortedDate(posts).slice(0,5)" 
        :key="index"
      >
        <td class="text-center" @click="openpostmodal(item)" style="cursor: pointer;">
          {{ item.title }}
        </td>
        <td class="text-center">
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