<script setup>
import { onMounted, ref } from 'vue';
import { VCard, VCardText, VCol, VDivider } from 'vuetify/lib/components/index.mjs';
import PostLikeVue from '@/views/like/PostLike.vue';

const posts = ref([])

async function getPosts() {
  try {
    const response = await fetch(
      `http://localhost:8080/api/post/read`,
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
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
    const response = await fetch(`http://localhost:8080/api/postlike/read/${postId}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
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

</script>

<template>
  <VCard class="position-relative">
    <VCardText class>
      <h5 class="text-h5"> 추천순 </h5>
    </VCardText>
    <VCardText>
      <VCol>
        <div class="mb-2" style="display: flex; justify-content: space-between;">
          <span>제목</span>
          <span class="mr-5">추천</span>
        </div>

        <VDivider class="mb-2"/>

        <div v-for="(item, index) in posts.slice(0,5)" :key="index" style="display: flex; justify-content: space-between;">
          <span> {{ item.title }}</span>
          <span> <PostLikeVue :postlikevue="item" /> </span>
        </div>
      </VCol>
    </VCardText>
  </VCard>
</template>
