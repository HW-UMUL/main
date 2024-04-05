<script setup>
import Post from '@/k_views/post/Post.vue';
import MyStarWikiList from '@/views/wiki/MyStarWikiList.vue'
import PostStarSort from '@/k_views/list/PostStarSort.vue';
import ViewWikiStarRank from '@/d_dashboard/ViewWikiStarRank.vue'



const serverAddress = inject('serverAddress')
const auth = inject('auth')

const posts = ref([])

async function getPosts(){

const response = await fetch(
    `http://${serverAddress}/api/poststar/read/my`,
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


getPosts()

const changePostWiki = ref(true)

</script>

<template>
  <VRow>
    <VCol
      cols="12"
      md="8"
      class="mb-4"
    >
    <VRow style="height: 30px; margin-bottom: 20px;">
      <div class="selectPostWiki" @click="changePostWiki=true">POST</div>
      <div class="bar"></div>
      <div class="selectPostWiki" @click="changePostWiki=false">WIKI</div>
    </VRow>
      <div v-if="changePostWiki" v-for="(item, index) in posts" :key="index">
        <Post :post="item.post" style="margin-bottom: 20px;"/>       
      </div>
      <div v-if="!changePostWiki">
        <MyStarWikiList/>
      </div>
    </VCol>  
    <VCol
      cols="12"
      md="4"
    >
      <VCard title="즐겨찾기순" style="margin-bottom: 20px">
        <PostStarSort style="margin-bottom: 20px" />
      </VCard>

      <ViewWikiStarRank style="margin-bottom: 20px;"/>

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
</style>
