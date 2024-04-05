<script setup>
import { useRouter } from "vue-router"
import axios from 'axios'
import avatar1 from '@images/avatars/avatar-1.png';

const serverAddress = inject('serverAddress')
const profileAddress = inject('profileAddress')

const auth = inject('auth')

// 토큰 브라우저에서 받아오기
let authToken = 'Bearer '

authToken = authToken + auth
const router = useRouter()

const info = ref({
  username: '',
  email: '',
  password: '',
  storeFileName: ''
})

async function getInfo(){

  const response = await fetch(
    `http://${serverAddress}/api/getinfo`,
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
    const res = await response.json()
    info.value.username = res[0]
    info.value.email = res[1]
    info.value.storeFileName = res[2]
  }
}

async function updateUsername(){

const response = await fetch(
  `http://${serverAddress}/api/update/username`,
  {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${auth}`,          
    },
    body: JSON.stringify(info.value.username),
    credentials: 'include'
  }  
)

if(!response.ok) {
  alert("실패!")
} else{
  alert('성공')
  router.push('/logout')
}
}

async function updateEmail(){

const response = await fetch(
  `http://${serverAddress}/api/update/email`,
  {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${auth}`,          
    },
    body: JSON.stringify(info.value.email),
    credentials: 'include'
  }  
)

if(!response.ok) {
  alert("실패!")
} else{
  alert('성공')
  router.push('/logout')
}
}

async function updatePassword(){

const response = await fetch(
  `http://${serverAddress}/api/update/password`,
  {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${auth}`,          
    },
    body: JSON.stringify(info.value.password),
    credentials: 'include'
  }  
)

if(!response.ok) {
  alert("실패!")
} else{
  alert('성공')
  router.push('/logout')

}
}
///////////// update image
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
    axios.put(`http://${serverAddress}/api/update/image`, formData, {
        headers: {
            'Content-Type': 'multipart/form-data',
            Authorization: authToken,
        }
    }).then(() => {
         getInfo()
    }).catch(() => {
        console.error('파일 전송 실패');
    });
}

///////////////

async function deleteAccount(){

const response = await fetch(
  `http://${serverAddress}/api/delete`,
  {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${auth}`,          
    },
    credentials: 'include'
  }  
)

if(!response.ok) {
  alert("못가 히히")
} else{
  alert('빠이!')
  router.push('/logout')
}
}


getInfo()
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
            
            <p >My Info</p>
            <img v-if="!info.storeFileName" class="follow-propile-img" :src="avatar1">
            <img v-else class="follow-propile-img" :src="profileAddress + info.storeFileName">
            <input type="file" id="file1" class="file-input">
            <button @click="sendFile">프로필 바꾸기</button>
                <VCol
                >
                  <VTextField
                  id="username"
                  v-model="info.username"
                  placeholder="이름"
                  label="이름"
                  />
                  <VBtn
                  color="secondary"
                  type="submit"
                  variant="outlined"
                  @click="updateUsername"
                >
                  update
                </VBtn>
                </VCol>
              

                <VCol
                >
                  <VTextField
                  id="email"
                  v-model="info.email"
                  placeholder="메일"
                  label="메일"
                  />
                  <VBtn
                  color="secondary"
                  type="submit"
                  variant="outlined"
                  @click="updateEmail"
                >
                  update
                </VBtn>
                </VCol>

                <VCol
                >
                  <VTextField
                  id="password"
                  v-model="info.password"
                  placeholder="비밀번호"
                  label="비밀번호"
                  />
                  <VBtn
                  color="secondary"
                  type="submit"
                  variant="outlined"
                  @click="updatePassword"
                >
                  update
                </VBtn>
                </VCol>

                <VCol
                >
                  <VBtn
                  color="secondary"
                  type="submit"
                  variant="outlined"
                  @click="deleteAccount"
                >
                  탈...퇴...
                </VBtn>
                </VCol>
        
          </div>
        </VCardText>
      </VCard>
    
    </VCol>  

  </VRow>
</template>

<style lang="scss">

.follow-propile-img {
  margin-top: 10px;
  margin-left: 10px;
   width: 100px;
   height: 100px;
  border-radius: 50%;
  object-fit: cover;
}
</style>
