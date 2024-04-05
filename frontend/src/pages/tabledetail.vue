<script setup>
import Post from '@/k_views/post/Post.vue';
import TableWikiList from '@/views/wiki/TableWikiList.vue'
import { useRouter } from "vue-router"
import avatar1 from '@images/avatars/avatar-1.png';
import { useDisplay } from 'vuetify'
import axios from 'axios'


const profileAddress = inject('profileAddress')

const { mdAndUp, mdAndDown } = useDisplay()

const serverAddress = inject('serverAddress')
const auth = inject('auth')
let authToken = 'Bearer '

authToken = authToken + auth

const router = useRouter()

const props = defineProps({
    tableId: Number
})

const table = ref([])
const oriTable = ref({
  name: '',
  description: ''
})
const tableUsers = ref([])
const inviteUsers = ref([{ email: '' }])
const adminUser = ref({
  email: ''
})

async function getTable(){

  const response = await fetch(
      `http://${serverAddress}/api/table/read/${props.tableId}`,
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
    table.value = await response.json()
    oriTable.value.name = table.value.name
    oriTable.value.description = table.value.description

  }
}

async function getTableUsers(){

  const response = await fetch(
      `http://${serverAddress}/api/tableuser/readuser/${props.tableId}`,
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
    tableUsers.value = await response.json()
  }
}

const addUser = () => {
  inviteUsers.value.push({ email: '' })
}

// 사용자 정보를 서버로 전송하는 함수
async function addTableUsers(){
  const formData = { email : [] }
  inviteUsers.value.forEach(function(user) {
    formData.email.push(user.email)
  })

  const response = await fetch(
    `http://${serverAddress}/api/tableuser/create/${props.tableId}`,
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
    getTableUsers()
  }
}

async function addAdminUser(){
  const response = await fetch(
    `http://${serverAddress}/api/tableuser/admin/add/${props.tableId}`,
    {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${auth}`,          
      },
      body: JSON.stringify(adminUser.value),
      credentials: 'include'
    }
  )

  if(!response.ok) {
    alert("실패!")
  } else{
    getTableUsers()
  }
}

async function removeAdminUser(){
  const response = await fetch(
    `http://${serverAddress}/api/tableuser/admin/remove/${props.tableId}`,
    {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${auth}`,          
      },
      body: JSON.stringify(adminUser.value),
      credentials: 'include'
    }
  )

  if(!response.ok) {
    alert("실패!")
  } else{
    getTableUsers()
  }
}



getTable()
getTableUsers()

const userManageDialog = ref(false)
function handleUserManageDialog(){
  if(userManageDialog.value == false){
    inviteUsers.value = [{ email: '' }]
    userManageDialog.value = true
  } else {
    userManageDialog.value = false
  }
}

const tableDialog = ref(false)
function handleTableDialog(){
  if(tableDialog.value == false){
    tableDialog.value = true
  } else {
    tableDialog.value = false
  }
}


async function updateTable(){
  const response = await fetch(
    `http://${serverAddress}/api/table/update/${props.tableId}`,
    {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${auth}`,          
      },
      body: JSON.stringify(table.value),
      credentials: 'include'
    }
  )

  if(!response.ok) {
    alert("실패!")
  } else{
    getTable()
  }
}

function createTablePost(){
  router.push(`/writepost/${props.tableId}`)
}

function createTableWiki(){
  router.push(`/writewiki/${props.tableId}`)
}



const posts = ref([])

async function getPosts(){

const response = await fetch(
    `http://${serverAddress}/api/post/read/table/${props.tableId}`,
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





//////////////////////////////////////// 초대, 화면 이동, create

const tables = ref([])
const invites = ref([])

async function getTables(){
  const response = await fetch(
      `http://${serverAddress}/api/tableuser/read/accept`,
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
    tables.value = await response.json()
  }
}

function moveTable(tableId){
  router.push(`/mytables/${tableId}`).then(() =>{
    getTable()
    getTableUsers()
    getPosts()
    test.value = tableId
  })
}

async function getInvites(){
  const response = await fetch(
      `http://${serverAddress}/api/tableuser/invite`,
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
    invites.value = await response.json()
  }
}

async function acceptInvite(tableUserId){
  const response = await fetch(
      `http://${serverAddress}/api/tableuser/update/${tableUserId}`,
      {
        method: 'PUT',
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
    getInvites()
    getTables()
  }
}

async function rejectInvite(tableUserId){
  const response = await fetch(
      `http://${serverAddress}/api/tableuser/delete/${tableUserId}`,
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
    alert("실패!")
  } else{
    getInvites()
  }
}

getInvites()
getTables()


/////////////////////////////////////////////////////////

const changePostWiki = ref(true)

////////////////// 파일 전송 //////////////////////////
const tableImageDialog = ref(false)
function sendFile() {
    var formData = new FormData();

    // 모든 파일 입력란에 대해 반복하면서 선택된 파일을 FormData에 추가
    var fileInputs = document.querySelectorAll('#table-image-input');
    fileInputs.forEach(input => {
    if (input.files.length > 0) {
        formData.append('files', input.files[0]);
    }
    });

    // FormData를 서버로 전송
    axios.put(`http://${serverAddress}/api/table/update/${props.tableId}/image`, formData, {
        headers: {
            'Content-Type': 'multipart/form-data',
            Authorization: authToken,
        }
    }).then(() => {
        console.log('파일 전송 성공');
        getTable()
        tableImageDialog.value=false

    }).catch(() => {
        console.error('파일 전송 실패');
    });
}
//////////////////////////////////////////////////////
</script>

<template>
  <VRow>
    <VCol
      cols="12"
      md="8"
      class="mb-4"
    >

      <div class="d-flex">
        <div class="table-image">
          <img @click="tableImageDialog=true" v-if="!table.profile" class="table-propile-img" :src="avatar1"/>
          <img @click="tableImageDialog=true" v-if="table.profile" class="table-propile-img" :src="profileAddress + table.profile.storeFileName"/>          
        </div>
        <div class="d-flex flex-column" >
          <div
          style="
          margin-left: 5%;
          font-size:40px;
          font-weight: 700;
          ">
          {{ oriTable.name }}
          </div>
          <div
          style="
          margin-bottom: 5%;
          ">
          </div>
          <div
          style="
          margin-left: 7%;
          font-size:18px;
          word-wrap: break-word;
          white-space: pre-line;
          ">
          {{ oriTable.description }}
          </div>
          <div class="d-flex mt-auto" style="margin-left:6%">
            <VBtn
            style="
            margin-right:5%;
            "
            @click="tableDialog=true"
            >
              Table Edit
            </VBtn>
            <VBtn
            @click="handleUserManageDialog"
            >
              User Manage
            </VBtn>
          </div>
        </div>
      </div>
    <VRow style="height: 30px; margin-top:20px; margin-bottom: 20px;">
      <div class="selectPostWiki" @click="changePostWiki=true">POST</div>
      <div class="bar"></div>
      <div class="selectPostWiki" @click="changePostWiki=false">WIKI</div>
    </VRow>





      <div v-if="changePostWiki" v-for="(item, index) in posts" :key="index">
        <Post :post="item" style="margin-bottom: 20px;"/>       
      </div>
      <div v-if="!changePostWiki">
        <TableWikiList v-bind:tableId="props.tableId"/>
      </div>


    </VCol>  
    <VCol
      cols="12"
      md="4" 
    >
      <VCard v-if="changePostWiki && mdAndUp" title="Create POST" @click="createTablePost" style="margin-bottom: 20px;"/>
      <VCard v-if="!changePostWiki && mdAndUp" title="Create WIKI" @click="createTableWiki" style="margin-bottom: 20px;"/>

      <VCard v-if="invites.length && mdAndUp" title="Invite Table" style="margin-bottom: 20px;">
        <div v-for="(item, index) in invites" :key="index">
          <VDivider/>
          <VListItem>
          <VRow>            
            <VCol
            cols="12"
            md="7"
            class="mb-4">
              {{ item.table.name }}
            </VCol>
            <VCol
            cols="12"
            md="5"
            class="mb-4">
            <span @click="acceptInvite(item.id)">Accept/</span>
            <span @click="rejectInvite(item.id)">Reject</span>
            </VCol>
          </VRow>
        </VListItem>
        </div>
      </VCard>

      <VCard v-if="tables.length && mdAndUp" title="My Table" style="margin-bottom: 20px;">
        <div v-for="(item, index) in tables" :key="index">
          <VDivider/>
            <div
              @click="moveTable(item.table.id)" class="d-flex">
                <div>
                <img v-if="!item.table.profile" class="my-table-img" :src="avatar1"/>
                <img v-if="item.table.profile"  class="my-table-img" :src="profileAddress + item.table.profile.storeFileName"/>          
                </div>
                <div class="my-table-name">
                {{ item.table.name }}
                </div>
            </div>
        </div>
      </VCard>
    </VCol>  

  </VRow>



  <!-- Dialog -->
            <VDialog v-model="tableImageDialog" max-width="50%">
              <VCard>
                <VCardTitle>Table Edit</VCardTitle>
                <VCardText>
                  <VCol>
                    <VFileInput
                      id="table-image-input"
                      placeholder="이름"
                      label="이름"
                    />
                  </VCol>
                  <VRow>
                    <VBtn
                    @click="sendFile"
                    class="me-5"
                    style="margin-top:10px;"
                    >
                    Submit
                    </VBtn>
                  </VRow>
                </VCardText>
                <VCardActions>
                  <!-- 팝업을 닫는 버튼 -->
                  <VBtn color="primary" @click="tableImageDialog = false">Close</VBtn>
                </VCardActions>
              </VCard>
            </VDialog>                    


            <VDialog v-model="tableDialog" max-width="50%">
              <VCard>
                <VCardTitle>Table Edit</VCardTitle>
                <VCardText>
                  <form @submit="updateTable">
                    <VCol
                    >
                      <VTextField
                      id="name"
                      v-model="table.name"
                      placeholder="이름"
                      label="이름"
                      />
                    </VCol>
                    <VCol
                    >
                      <VTextarea
                      id="description"
                      v-model="table.description"
                      placeholder="설명"
                      label="설명"
                      />

                    </VCol>

                    <VRow>
                    <VRadioGroup
                    v-model="table.isPublic" 
                    inline>
                      <VRadio
                      label="public"
                      :value=true>
                      </VRadio>
                      <VRadio
                      label="private"
                      :value=false>
                      </VRadio>
                    </VRadioGroup>
                    <VBtn
                      type="submit"
                      class="me-5"
                      style="margin-top:10px;"
                    >
                      Submit
                    </VBtn>
                  </VRow>
                  </form>               
                </VCardText>
                <VCardActions>
                  <!-- 팝업을 닫는 버튼 -->
                  <VBtn color="primary" @click="tableDialog = false">Close</VBtn>
                </VCardActions>
              </VCard>
            </VDialog>



            <VDialog v-model="userManageDialog" max-width="50%">
              <VCard>
                <VCardTitle>Invite</VCardTitle>
                <VCardText>
                  <form @submit="addTableUsers">
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
                <VCardTitle>Add Admin</VCardTitle>
                <VCardText>
                  <form @submit="addAdminUser">
                    <VTextField
                      id="email"
                      v-model="adminUser.email"
                      placeholder="email"
                      label="email"
                      style="margin-bottom: 5px;"
                      />
                      <VBtn type="submit">Submit</VBtn>
                  </form>               
                </VCardText>
                <VCardTitle>Remove Admin</VCardTitle>
                <VCardText>
                  <form @submit="removeAdminUser">
                    <VTextField
                      id="email"
                      v-model="adminUser.email"
                      placeholder="email"
                      label="email"
                      style="margin-bottom: 5px;"
                      />
                      <VBtn type="submit">Submit</VBtn>
                  </form>               
                </VCardText>
                <VCardActions>
                  <!-- 팝업을 닫는 버튼 -->
                  <VBtn color="primary" @click="userManageDialog = false">Close</VBtn>
                </VCardActions>
              </VCard>
            </VDialog>




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

.table-image{
  width: 30%;
  height:100%;
}
.table-info{
  width: 70%;
}

.table-propile-img {
  margin-left: 5%;
   width: 26dvh;
   height: 26dvh;
  border-radius: 50%;
  object-fit: cover;
}

.my-table-img{
  margin-top:10%;
  margin-left: 10%;
   width: 50px;
   height: 50px;
  border-radius: 50%;
  object-fit: cover;
}

.my-table-name{
  display: flex; 
  align-items: center;
  margin-left: 5%;
  font-size: 20px;
  color: black;
}
</style>