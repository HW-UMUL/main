<script setup>
import { VCol, VRow, VTextField, VTextarea } from 'vuetify/lib/components/index.mjs';


const wiki = ref({
  title: '',
  content: '',
  tag: ''
})

async function writeWiki(){

  const formData = {
    title: wiki.value.title,
    content: post.value.content,
    tag: wiki.value.tag
  }

  const response = await fetch(
      `http://localhost:8080/api/wiki/create`,
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
            <form @submit.prevent="writeWiki()">
                <p >Wiki 작성</p>
                <VCol
                >
                  <VTextField
                  id="title"
                  v-model="wiki.title"
                  placeholder="제목"
                  label="제목"
                  />
                </VCol>

              

                <VCol
                >
                  <VTextarea
                  id="content"
                  v-model="wiki.content"
                  placeholder="본문"
                  label="본문"
                  />
                </VCol>



                <VCol
                >
                  <VTextField
                  id="tag"
                  v-model="wiki.tag"
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

              <!-- <div><label>제목</label><input type="text" v-model="post.title"></input></div>
              <div><label>본문</label><input type="text" v-model="post.content"></input></div>
              <div><label>카테고리</label><input type="text" v-model="post.category"></input></div>
              <div><label>태그</label><input type="text" v-model="post.tag"></input></div>
              <div><input type="submit"></input></div> -->
            </form>
          </div>
        </VCardText>
      </VCard>
    
    </VCol>  

  </VRow>
</template>
