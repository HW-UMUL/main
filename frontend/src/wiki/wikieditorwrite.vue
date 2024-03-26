<script setup>
import MyEditor from '@/wiki/wikieditor.vue'
import axios from 'axios'

// 토큰 브라우저에서 받아오기
let authToken = 'Bearer '
const cookies = document.cookie.split(';')
let jwtToken = ''

for (let i = 0; i < cookies.length; i++) {
  const cookie = cookies[i].trim()
  // 쿠키 이름이 'jwtToken'으로 시작하는 경우
  if (cookie.startsWith('jwtToken=')) {
    // 'jwtToken'의 값만 추출
    jwtToken = cookie.substring('jwtToken='.length)
    break
  }
}
authToken = authToken + jwtToken

const formData = {
  title: '',
  category: '',
  content: '',
  tag: '',
}

function submitForm() {
  // 헤더 정보 설정
  const headers = {
    'Content-Type': 'application/json',
    Authorization: authToken,
  }

  // 서버로 보낼 데이터
  const data = JSON.stringify(formData)

  // POST 요청 보내기
  axios
    .post('http://localhost:8080/api/wiki/create', data, { headers })
    .then(response => {
      console.log('서버로부터의 응답:', response.data)
      // 서버로부터의 응답에 따라 적절한 동작을 수행할 수 있음.
      // ex) 성공 메시지를 표시하거나 페이지를 리디렉션할 수 있음.

      // const router = useRouter()  -- 라우터로 리다이렉트 하기 위한 코드. 잘 안됨. 보류.
      // router.push(route.query.redirect || '/dashboard');
      window.location.href = 'http://localhost:5173/dashboard'
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
      <!-- <textarea
        name="content"
        id="content"
        cols="30"
        rows="10"
        class="w-full"
        v-model="formData.content"
      ></textarea> -->
      <div>
        <input
          type="text"
          class="w-full border border-gray-400 p-2"
          v-model="formData.tag"
          placeholder="tag"
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
          Create Post
        </button>
      </div>
    </form>
  </div>
</template>
