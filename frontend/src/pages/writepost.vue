<script setup>
import { VCol, VRow, VTextField, VTextarea } from 'vuetify/lib/components/index.mjs';
import PostLikeSort from '@/views/list/PostLikeSort.vue';
import PostDateSort from '@/views/list/PostDateSort.vue';


const post = ref({
  title: '',
  content: '',
  category: '',
  tag: ''
})

async function writePost(){

  const formData = {
    title: post.value.title,
    content: post.value.content,
    category: post.value.category,
    tag: post.value.tag
  }

  const response = await fetch(
      `http://localhost:8080/api/post/create`,
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
    window.location.href = 'http://localhost:5173/'
  }
}

</script>

<template>
  <VRow>
    <VCol
      cols="12"
      md="8"
      class="mb-4"
    >
      <VCard class="position-relative">
        <VCardText>
          <div >
            <form @submit.prevent="writePost()">
            <p >Post 작성</p>
                <VCol
                >
                  <VTextField
                  id="title"
                  v-model="post.title"
                  placeholder="제목"
                  label="제목"
                  />
                </VCol>

              

                <VCol
                >
                  <VTextarea
                  id="content"
                  v-model="post.content"
                  placeholder="본문"
                  label="본문"
                  />
                </VCol>



                <VCol
                >
                  <VTextField
                  id="category"
                  v-model="post.category"
                  placeholder="카테고리"
                  label="카테고리"
                  />
                </VCol>



                <VCol
                >
                  <VTextField
                  id="tag"
                  v-model="post.tag"
                  placeholder="태그"
                  label="태그"
                  />
                </VCol>


              <VCol cols="12">
                <VBtn
                  type="submit"
                  class="me-5"
                >
                  Submit
                </VBtn>

                <VBtn
                  color="secondary"
                  type="reset"
                  variant="outlined"
                >
                  Reset
                </VBtn>
              </VCol>

            </form>
          </div>
        </VCardText>
      </VCard>
    
    </VCol>
    
    <VCol
      cols="12"
      md="4"
    >
      <VCard title="추천순" style="margin-bottom: 20px">
      <PostLikeSort style="margin-bottom: 20px" />
      </VCard>    
      <VCard title="최신순" style="margin-bottom: 20px">
        <PostDateSort />
      </VCard>
      <AnalyticsAward style="margin-bottom: 20px"/>
      <AnalyticsAward style="margin-bottom: 20px"/>
    </VCol>

  </VRow>
</template>
