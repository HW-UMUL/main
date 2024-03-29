<script setup>
import { onMounted } from 'vue';
import axios from 'axios'

const serverAddress = inject('serverAddress')
const auth = inject('auth')

// 토큰 브라우저에서 받아오기
let authToken = 'Bearer '
let jwtToken = auth

authToken = authToken + auth

function sendFile() {
    var formData = new FormData();

    // 모든 파일 입력란에 대해 반복하면서 선택된 파일을 FormData에 추가
    var fileInputs = document.querySelectorAll('.file-input');
    fileInputs.forEach(input => {
    if (input.files.length > 0) {
        formData.append('files', input.files[0]);
    }
    });

    // FormData를 서버로 전송
    axios.post(`http://${serverAddress}/api/file/create`, formData, {
        headers: {
            'Content-Type': 'multipart/form-data',
            Authorization: authToken,
        }
    }).then(() => {
        console.log('파일 전송 성공');
    }).catch(() => {
        console.error('파일 전송 실패');
    });
}
const imageUrl = ref()
function getFile(){
    axios.get(`http://${serverAddress}/api/file/read`, {
        headers: {
            Authorization: authToken,
        },
        responseType: 'blob'
    }).then((res) => {
        imageUrl.value = URL.createObjectURL(res.data)
    }).catch(() => {
        console.error('실패');
    });
}
getFile()
</script>


<template>
  <div>
    <!-- 여러 파일 선택 입력란 -->
    <label for="file1">
      <input type="file" id="file1" class="file-input">
    </label>
    <label for="file2">
      <input type="file" id="file2" class="file-input">
    </label>
    <!-- 추가 입력란이 필요한 경우 위와 같은 방식으로 반복 가능 -->
    
    <!-- 파일 전송 버튼 -->
    <button @click="sendFile">전송</button>
  </div>

  <img :src="imageUrl" alt="이미지" />

  </template>
