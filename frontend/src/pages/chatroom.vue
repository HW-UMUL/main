<script setup>
import { onMounted, onUpdated } from 'vue';
import socketModule from '/test/index.js'
import avatar1 from '@images/avatars/avatar-1.png';


const serverAddress = inject('serverAddress')
const auth = inject('auth')
const myname = sessionStorage.getItem("myname")
const check = ref(true)
// websocket

socketModule.setConnectionString(`ws://${serverAddress}/ws`)
socketModule.connect()

socketModule.onOpen(() => {
  const formData = { auth: auth }
  socketModule.socket.value.send(JSON.stringify(formData))
})

socketModule.onMessage(message => {
    const jsonData = JSON.parse(message.data)
    jsonData.date = new Date(jsonData.date)
    chats.value.push(jsonData)
    check.value = !check.value
})

socketModule.onClose(() => {
  socketModule.connect()
})
//socketModule.socket.value.push
//socketModule.socket.value.close();


///////


const props = defineProps({
    roomId: String
})

// 초기에 빈 배열로 시작
const inviteUsers = ref([{ email: '' }])
const chatRoomUsers = ref([])

const chats = ref([])

// 사용자 정보를 서버로 전송하는 함수
async function addChatRoomUsers(){
  const formData = { email : [] }
  inviteUsers.value.forEach(function(user) {
    formData.email.push(user.email)
  })

  inviteUsers.value = [{ email : ''}]

  const response = await fetch(
    `http://${serverAddress}/api/chatroomuser/create/${props.roomId}`,
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
    getChatRoomUsers()
  }
}

async function getChatRoomUsers(){
  const response = await fetch(
    `http://${serverAddress}/api/chatroomuser/read/${props.roomId}`,
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
    chatRoomUsers.value = await response.json()
  }
}

// 새로운 사용자 추가하는 함수
const addUser = () => {
  inviteUsers.value.push({ email: '' })
}

// socket.value.send(text.value)

const message = ref('')
async function sendMessage(){
  const formData = {
    content: message.value
  }
  const response = await fetch(
    `http://${serverAddress}/api/chat/create/${props.roomId}`,
    {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${auth}`,          
      },
      credentials: 'include',
      body: JSON.stringify(formData)
    }
  )

  if(!response.ok) {
    alert("실패!")
  } else{
    message.value = ''
    const chatBoard = document.querySelector('.chat-board')
    chatBoard.scrollTop = chatBoard.scrollHeight
    getMessages()
    scrollTop()
  }}

async function getMessages(){
  const response = await fetch(
    `http://${serverAddress}/api/chat/read/${props.roomId}`,
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
    chats.value = await response.json()
  }
}

function scrollTop(){
  const chatBoard = document.querySelector('.chat-board')
    chatBoard.scrollTop = chatBoard.scrollHeight
}

onMounted(() => {
  getMessages().then(() => {
    scrollTop()
  })
})

onUpdated(chats => {
  console.log('update!!')
  scrollTop()
})

getChatRoomUsers()  


const inviteUserDialog = ref(false)



function formatDateTime(dateTimeString) {
  const dateTime = new Date(dateTimeString); // 문자열을 Date 객체로 변환

  // 시간을 12시간 형식으로 변환
  let hours = dateTime.getHours();
  const ampm = hours >= 12 ? '오후' : '오전';
  hours %= 12;
  hours = hours || 12; // 0시를 12시로 표시

  // 분을 두 자리 수로 포맷
  const minutes = String(dateTime.getMinutes()).padStart(2, '0');

  // 결과 문자열 생성
  const formattedTime = `${ampm} ${hours}:${minutes}`;

  return formattedTime;
}


</script>

<template>
  <table>
    <tbody>
      <tr>
        <td>test1</td>
        <td>test2</td>
      </tr>
      <tr>
        <td>test3</td>
        <td>test4</td>
      </tr>
    </tbody>

  </table>
  <div style="display: none;">{{ check }}</div>
  <VRow>
    <VCol
      cols="12"
      md="8"
      class="mb-4"
    >
<!--
          {{ chat.user.username }} - {{ chat.content }} - {{ chat.date }}

-->    
    <div class="chat-board">
      <div v-for="(chat, index) in chats" :key="index">
        <div v-if="chat.user.username===myname" class="d-flex flex-row-reverse">
          <div class="ma-2 pa-2 my-chat-box">
              {{ chat.content }}
          </div>     
          <div class="chat-date">{{ formatDateTime(chat.date) }}</div>
        </div>
        <div v-else>
          <table>
            <tr>
              <td>
                <img class="other-propile-img" :src="avatar1">                
              </td>
              <td class="d-flex">
                <div style="margin-left: 10px;">{{ chat.user.username }}</div>
            <div class="ma-2 pa-2 other-chat-box">
              {{ chat.content }}     
          </div>


              </td>
            </tr>
          </table>
          <!--
          <img class="other-propile-img" :src="avatar1">
            <div style="margin-left: 10px;">{{ chat.user.username }}</div>
            <div class="ma-2 pa-2 other-chat-box">
              {{ chat.content }}     
          </div>
          <div class="other-chat-date">{{ formatDateTime(chat.date) }}</div>  

          -->
        </div>       
      </div>
      <VRow style="width:100%">
        <VCol style="min-width:77%">
          <VTextField
          id="message"
          placeholder="Message"
          label="Message"
          v-model="message"
          style="margin-left: 10px;"
          />
       </VCol>
       <VCol >
          <VBtn @click="sendMessage" style="margin-left: 10px; width:100%">Submit</VBtn>
        </VCol>
      </VRow>
    </div>
   </VCol>  
    <VCol
      cols="12"
      md="4"
    >
      <VCard title="Invite" @click="inviteUserDialog=true" style="margin-bottom: 20px;"/>

    </VCol>  
  </VRow>



            <VDialog v-model="inviteUserDialog" max-width="50%">
              <VCard>
                <VCardTitle>Invite</VCardTitle>
                <VCardText>
                  <form @submit="addChatRoomUsers">
                    <div v-for="(user, index) in inviteUsers" :key="index">
                      <VTextField
                      id="email"
                      v-model="user.email"
                      placeholder="email"
                      label="email"
                      style="margin-bottom: 5px;"
                      />
                    </div>
                    <VBtn 
                    type="button" 
                    @click="addUser"
                    style="margin-right: 5px;">Add User</VBtn>
                    <VBtn type="submit">Submit</VBtn>
                  </form>               
                </VCardText>
                <VCardActions>
                  <!-- 팝업을 닫는 버튼 -->
                  <VBtn color="primary" @click="inviteUserDialog = false">Close</VBtn>
                </VCardActions>
              </VCard>
            </VDialog>

</template>
<style>
/* 수직 스크롤바 스타일 */
::-webkit-scrollbar {
  width: 10px; /* 스크롤바의 너비 */
}

::-webkit-scrollbar-track {
  background: #f1f1f1; /* 스크롤바 트랙 배경색 */
}

::-webkit-scrollbar-thumb {
  background: #888; /* 스크롤바 색상 */
}

/* Chat Box 
      background-color: #f0f0f0;
      max-width: 55%;

*/

.chat-board {
  height: 80dvh; 
  overflow-y:scroll; 
  background-color: rgba(220, 145, 196, 0.393);
  border-radius: 10px 10px 10px 10px; /* 오른쪽 상단 귀퉁이만 직각으로 만듦 */

}
.my-chat-box {
      max-width: 55%;
      background-color: rgb(255, 255, 255);
      border: 2px solid #333;
      border-radius: 10px 0 10px 10px; /* 오른쪽 상단 귀퉁이만 직각으로 만듦 */
      word-wrap: break-word;
      white-space: pre-line;
}

.other-chat-box {
      max-width: 55%;
      background-color: rgb(255, 255, 255);
      border: 2px solid #333;
      border-radius: 0 10px 10px 10px; /* 오른쪽 상단 귀퉁이만 직각으로 만듦 */
      word-wrap: break-word;
      white-space: pre-line;
}

.chat-date {
  display: flex;
  align-items: flex-end; 
  margin-bottom: 5px;
}

.other-chat-date {
  background-color: green;
  display: flex;
  align-items: flex-end; 
  margin-bottom: 5px;
}


.other-propile-img {
  margin-left: 2px;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
}
</style>