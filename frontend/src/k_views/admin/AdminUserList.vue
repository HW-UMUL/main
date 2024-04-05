<script setup>
import { ref } from 'vue';

const serverAddress = inject('serverAddress')
const auth = inject('auth')

const sortDirection = ref('desc')
const sortBy = ref('id')

function togglesort(sortType) {
  if (sortBy.value === sortType) {
    sortDirection.value = sortDirection.value === 'asc' ? 'desc' : 'asc'
  } else {
    sortBy.value = sortType
    sortDirection.value = 'asc'
  }
  issort();
}

function issort() {
  sortBy.value === 'id'
  sortBy.value === 'username'
  sortBy.value === 'role'
  const checkedUserIds = checkedUsers.value.map((checked, index) => checked ? sortedUserinfo.value[index].id : null);
  sortedUserinfo.value.sort((a, b) => {
    const compareResult = sortDirection.value === 'asc' ? -1 : 1
    switch (sortBy.value) {
      case 'id':
        return compareResult * (a.id - b.id);
      case 'username':
        return compareResult * (a.username.localeCompare(b.username))
      case 'email':
        return compareResult * (a.email.localeCompare(b.email))
      case 'role':
        return compareResult * (a.roles[0].localeCompare(b.roles[0]))
      default:
        return 0;
    }
  })
  checkedUsers.value = sortedUserinfo.value.map(user => checkedUserIds.includes(user.id));
}

const sortedUserinfo = ref([])
const userinfo = ref([])

async function getuserInfo(){

  const response = await fetch(
      `http://${serverAddress}/api/userinfo`,
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
    console.error(error)
  } else{
    userinfo.value = await response.json()
    await Promise.all(userinfo.value.map(async (user) => {
      user.roles = await getuserrole(user.id)
    }))
    sortedUserinfo.value = [...userinfo.value];
    issort()
  }
}

async function getuserrole(userId){

  const response = await fetch(
      `http://${serverAddress}/api/userrole/${userId}`,
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
    console.error(error)
  } else{
    const roles = await response.json()
    return roles.map(item => item.role.role.substring(5))
  }
}

getuserInfo()

// 현재 유저
const currentUser = ref({
  username: '',
  email: '',
  password: ''
})

async function getinfo(){

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
    console.error()
  } else{
    const res = await response.json()
    currentUser.value.username = res[0]
    currentUser.value.email = res[1]
  }
}
getinfo()

// 삭제
async function delUser(username) {

  const response = await fetch(
    `http://${serverAddress}/api/delete/${username}`,
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
    console.error()
  } else {
    // alert("삭제되었습니다")
    // window.location.reload()
  }
}

const checkedUsers = ref([])
async function delcheckedUsers() {
  const delUserList = [];
  let includeCurrentInfo = (0);

  checkedUsers.value.forEach((checked, index) => {
    if(sortedUserinfo.value[index].username == currentUser.value.username) {
        alert("자신은 삭제할 수 없습니다.");
        includeCurrentInfo = 1;
        checkedUsers.value = [];
        allChecked.value = !allChecked.value;
        return;
    }
    if (checked) {
      delUserList.push(sortedUserinfo.value[index].username);
    }
  })
  if(includeCurrentInfo == 1) { return; }
  if(delUserList.length === 0) {
    alert("삭제할 게시물을 선택하십시오.");
    return;
  }
  const confirmdel = confirm("삭제하시겠습니까?");
  if(!confirmdel) return;

  try {
    for(const username of delUserList) {
      await delUser(username);
    }
    checkedUsers.value = [];
    alert("삭제되었습니다");
  } catch(error) {
    console.error(error);
    alert("삭제 실패");
  }
}

// 체크
const allChecked = ref(false);

function selectAllCheckboxes() {
  checkedUsers.value = sortedUserinfo.value.map(() => allChecked.value);
}

function selectIndividualCheckbox(index) {
  checkedUsers.value[index] = !checkedUsers.value[index];
  allChecked.value = checkedUsers.value.every(checked => checked);
}
</script>

<template>
<div style="margin: 10px;
  display: flex;
  justify-content: space-between">
  <span
    style="font-size: small;
    margin-left: 10px;">
    total : {{ userinfo.length }}
  </span>
  <VIconBtn
    style="margin-right: 10px;
    cursor: pointer"
    @click="delcheckedUsers"
    >
    삭제
  </VIconBtn>
</div>
<VTable
density="comfortable"
fixed-header
height="440"
>
  <thead>
    <tr>
      <th class="text-center">
        No
        <VIcon size="12pt"
          :icon="sortBy === 'id' ? (sortDirection === 'asc' ? 'ri-arrow-up-s-line' : 'ri-arrow-down-s-line') : 'ri-subtract-line'"
          @click="togglesort('id')" />
      </th>
      <th class="text-center" >
        username
        <VIcon size="12pt"
          :icon="sortBy === 'username' ? (sortDirection === 'asc' ? 'ri-arrow-up-s-line' : 'ri-arrow-down-s-line') : 'ri-subtract-line'"
          @click="togglesort('username')" />
      </th>
      <th class="text-center">
        E-mail
        <VIcon size="12pt"
          :icon="sortBy === 'email' ? (sortDirection === 'asc' ? 'ri-arrow-up-s-line' : 'ri-arrow-down-s-line') : 'ri-subtract-line'"
          @click="togglesort('email')" />
      </th>
      <th class="text-center">
        role
        <VIcon size="12pt"
          :icon="sortBy === 'role' ? (sortDirection === 'asc' ? 'ri-arrow-up-s-line' : 'ri-arrow-down-s-line') : 'ri-subtract-line'"
          @click="togglesort('role')" />
      </th>
      <th class="text-center">
        <input 
          type="checkbox"
          v-model="allChecked"
          @change="selectAllCheckboxes">
      </th>
    </tr>
  </thead>

  <tbody>
    <tr v-for="(item, index) in sortedUserinfo" :key="index">
      <td class="text-center" style="width: 60pt">
        {{ item.id }}
      </td>
      <td class="text-center">
        {{ item.username }}
      </td>
      <td class="text-center">
        {{ item.email }}  
      </td>
      <td class="text-center">
        {{ item.roles.join(', ').replace(/"/g, '') }}
        <!-- {{ item.roles }} -->
      </td>
      <td class="text-center" style="width: 50px;">
        <input 
          type="checkbox"
          :checked="checkedUsers[index]"
          @change.stop="selectIndividualCheckbox(index)"
          >
      </td>
    </tr>
  </tbody>
</VTable>

</template>
