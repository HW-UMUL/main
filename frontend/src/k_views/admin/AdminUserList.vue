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
</script>

<template>
<div
  style="font-size: small;
  margin: 10px;">
  total : {{ userinfo.length }}
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
    </tr>
  </tbody>
</VTable>

<div class="modal-wrap" v-if="ispostmodal" @click="closepostmodal">
  <div class="modal-container" @click.stop="">
    <div class="modal-body" v-if="selectedPost">

    </div>
  </div>
</div>
</template>

<style lang="scss">
/* dimmed */
.modal-wrap {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.2);
  z-index: 100;
}
/* modal or popup */
.modal-container {
  position: relative;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 550px;
  // background: #fff;
  border-radius: 10px;
  padding: 20px;
  box-sizing: border-box;
}
.modal-body {
  max-height: calc(100vh - 200px);
  overflow-y: auto;
}
</style>