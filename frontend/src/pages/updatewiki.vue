<script setup>
import MyEditor from '@/pages/wikieditor.vue'
import axios from 'axios'
import { onMounted } from 'vue'

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
console.log('토큰:', authToken)

const route = useRoute()
const wiki = ref(null)

console.log('wiki', wiki)
const formData = reactive({
  title: '',
  category: '',
  content: '',
  tag: '',
})

onMounted(async () => {
  try {
    const res = await axios.get(`http://localhost:8080/api/wiki/read/${route.params.id}`, {
      headers: {
        Authorization: authToken,
      },
    })

    wiki.value = res.data

    formData.title = res.data.title
    formData.category = res.data.category
    formData.content = res.data.content
    formData.tag = res.data.tag

    console.log('내부:', formData)
  } catch (error) {
    console.log('에러 발생: ', error)
  }
})

const submitForm = async () => {
  const headers = {
    'Content-Type': 'application/json',
    Authorization: authToken,
  }
  // 서버로 보낼 데이터
  const data = JSON.stringify(formData)

  try {
    axios.put(`http://localhost:8080/api/wiki/update/${route.params.id}`, data, { headers }).then(res => {
      console.log('updatewiki:', res.data)
      window.location.href = `http://localhost:5173/readwiki/${route.params.id}`
    })
  } catch (error) {
    console.error('에러 발생: ', error)
  }
}

const handleContentUpdate = content => {
  formData.content = content
}
</script>

<style scoped>
table {
  border-collapse: collapse;
  border-radius: 10px;
  border-style: hidden;
  box-shadow: 0 0 0 1px #000;
}

td,
th {
  border: 3px solid #ccc; /* 테두리 스타일 및 색상을 설정합니다. */
}
</style>
<template>
  <form @submit.prevent="submitForm">
    <table
      border="1"
      width="90%"
      height="700"
    >
      <tr>
        <td>
          <input
            type="text"
            v-model="formData.title"
            placeholder="title"
            style="padding-left: 20px; padding-top: 10px; padding-bottom: 10px; width: 100%"
          />
        </td>
      </tr>
      <tr>
        <td>
          <input
            type="text"
            v-model="formData.category"
            placeholder="category"
            style="padding-left: 20px; padding-top: 10px; padding-bottom: 10px"
          />
        </td>
      </tr>
      <tr>
        <td>
          <!-- <textarea
            placeholder="content"
            v-model="formData.content"
            rows="25"
            cols="130"
            style="padding-left: 20px; padding-top: 10px; padding-bottom: 10px"
          ></textarea> -->
          <div>
            <MyEditor
              v-model:modelValue="formData.content"
              @contentUpdated="handleContentUpdate"
            />
          </div>
        </td>
      </tr>
      <tr>
        <td>
          <input
            type="text"
            v-model="formData.tag"
            placeholder="tag"
            style="padding-left: 20px; padding-top: 10px; padding-bottom: 10px; width: 100%"
          />
        </td>
      </tr>
      <tr align="center">
        <td>
          <button
            style="
              background-color: #905dff;
              border: none;
              color: white;
              padding: 7px 15px;
              text-align: center;
              text-decoration: none;
              display: inline-block;
              border-style: solid;
              font-size: 15px;
            "
            type="submit"
          >
            작성
          </button>
          <button
            style="
              background-color: #905dff;
              border: none;
              color: white;
              padding: 7px 15px;
              text-align: center;
              text-decoration: none;
              display: inline-block;
              border-style: solid;
              font-size: 15px;
            "
            @click="location.href = 'this.$router.go(-1)'"
          >
            취소
          </button>
        </td>
      </tr>
    </table>
  </form>
</template>
