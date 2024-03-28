<script setup>
import MyEditor from '@/d_wiki/wikieditor.vue'
import axios from 'axios'
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()

const serverAddress = inject('serverAddress')
const auth = inject('auth')

// 토큰 브라우저에서 받아오기
let authToken = 'Bearer '
let jwtToken = auth

authToken = authToken + jwtToken

const formData = ref({
  title: '',
  content: '',
  category: ''
})

if(route.params.id){

  axios
  .get(`http://${serverAddress}/api/wiki/read/${route.params.id}`, {
    headers: {
      Authorization: authToken,
    },
  })
  .then(res => {
    formData.value.title = res.data.title
    formData.value.content = res.data.content
    formData.value.category = res.data.category.name
  })


}



function submitForm() {
  // 헤더 정보 설정
  const headers = {
    'Content-Type': 'application/json',
    Authorization: authToken,
  }

  // 서버로 보낼 데이터
  const data = JSON.stringify(formData.value)

  console.log(data)
  // POST 요청 보내기
  axios
    .post(`http://${serverAddress}/api/wiki/create`, data, { headers })
    .then(response => {
      console.log('서버로부터의 응답:', response.data)

      
//      router.push({ path: '/wiki' })
      router.go(-1)
    })
    .catch(error => {
      console.error('에러 발생:', error)
      // 에러 처리 로직을 추가할 수 있습니다.
    })
}
const props = defineProps({
  posts: Array,
})
const exampleContent = `<p>This is <strong>bold</strong> and <em>italic</em> text.</p>`
const renderedContent = ref(exampleContent)
</script>

<template>
  <div class="container mx-auto max-w-4xl my-8">
    <form
      @submit.prevent="submitForm"
      class="space-y-8"
    >
      <input
        type="text"
        class="w-full border border-gray-400 p-2"
        v-model="formData.title"
        placeholder="title"
        style="padding-left: 10px"
      />

      <div>
        <MyEditor v-model="formData.content" />
      </div>
      <div>
        <input
          type="text"
          class="w-full border border-gray-400 p-2"
          v-model="formData.category"
          placeholder="category"
          style="padding-left: 10px"
        />
      </div>
      <br />
      <div>
        <button
          type="submit"
          class="bg-blue-600 text-white p-2 rounded"
          style="background-color: #905dff"
        >
          Create Wiki
        </button>
      </div>
    </form>
  </div>
</template>
