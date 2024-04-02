<script setup>
import { VCol, VRow, VTextField, VTextarea } from 'vuetify/lib/components/index.mjs';
import PostLikeSort from '@/k_views/list/PostLikeSort.vue';
import PostDateSort from '@/k_views/list/PostDateSort.vue';
import TiptapEditor from '@/k_components/TiptapEditor.vue';
import { useRouter } from "vue-router"

const router = useRouter()
const serverAddress = inject('serverAddress')
const auth = inject('auth')

const post = ref({
  title: '',
  content: '',
  tag: ''
})

async function writePost(){

  const formData = {
    title: post.value.title,
    content: post.value.content,
    tag: post.value.tag
  }

  const response = await fetch(
      `http://${serverAddress}/api/post/create`,
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${auth}`, 
        },
        body: JSON.stringify(formData),
        credentials: 'include'
      }
  )

  if(!response.ok) {
    alert("실패!")
  } else{
    router.push(`/`) 
  }
}

// const textareaRef = ref(null);
// const isFocused = ref(false);

// const handleInput = () => {
//   console.log(textareaRef.value.innerText);
// }

// const handleClick = () => {
//   isFocused.value = true;
// }

// // 클릭이 아닌 다른 곳을 클릭하면 포커스 상태를 false로 변경
// window.addEventListener('click', (event) => {
//   if (!textareaRef.value.contains(event.target)) {
//     isFocused.value = false;
//   }
// });
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
          <p >Post 작성</p>
          <VCol
          > 
            <VTextField
            id="title"
            v-model="post.title"
            placeholder="제목"
            label="제목"
            >
            </VTextField>
          </VCol>

          <VCol
          >
            <!-- <div>
            <TiptapEditor
            class="tiptap"
            id="Tipcontent"
            v-model="post.content"
            />
            </div> -->
            <VTextarea
            id="content"
            v-model="post.content"
            placeholder="본문"
            label="본문"
            />

          </VCol>

          <!-- <VCol
          >
            <VTextField
            id="category"
            v-model="post.category"
            placeholder="카테고리"
            label="카테고리"
            />
          </VCol> -->

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
              @click="writePost()"
              type="submit"
              class="me-5"
            >
              Submit
            </VBtn>
            
            <!-- <VBtn
              color="secondary"
              type="reset"
              variant="outlined"
            >
              Reset
            </VBtn> -->
          </VCol>

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
    </VCol>

  </VRow>
</template>
<style lang="scss">
.tiptap {
  border: 1px solid #D1CFD4;
  border-radius: 6px;
  padding: 14px;
  min-height: 300px;
  max-height: 300px;
  overflow-y: auto;
  transition: border-color 0.3s ease;
}
.tiptap:focus {
  border:2px solid #d50000;
}
.tiptap:not(:focus) {
  border: 1px solid #ccc;
}
</style>