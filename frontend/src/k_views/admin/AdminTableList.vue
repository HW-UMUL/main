<script setup>

// 년-월-일
const formatDate = function(value) {
    const date = new Date(value);
    const year = date.getFullYear().toString().slice(-2);
    const month = (date.getMonth() + 1);
    const day = date.getDate();
    const hour = date.getHours();
    const min = date.getMinutes();
    const sec = date.getSeconds();
    
    return `${year}-${month}-${day} ${hour}:${min}`;
}

const auth = inject('auth')
const serverAddress = inject('serverAddress')

const tables = ref([])

  async function getTables(){

  const response = await fetch(
      `http://${serverAddress}/api/table/readtables`,
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
    tables.value = await response.json()
  }
}

getTables()
</script>

<template>
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
         :icon="isnumsort ? 'ri-arrow-up-s-line' : 'ri-arrow-down-s-line'"
          @click="numsort" />
      </th>
      <th class="text-center" >
        Name
        <VIcon size="12pt"
         :icon="isnamesort ? 'ri-arrow-up-s-line' : 'ri-arrow-down-s-line'"
          @click="namesort" />
      </th>
      <th class="text-center">
        Description
      </th>
      <th class="text-center">
        date
        <VIcon size="12pt"
         :icon="isrolesort ? 'ri-arrow-up-s-line' : 'ri-arrow-down-s-line'"
          @click="rolesort" />
      </th>
      <th class="text-center">
        
      </th>
    </tr>
  </thead>

  <tbody>
    <tr v-for="(item, index) in tables" :key="index">
      <td class="text-center" style="width: 60pt">
        {{ index+1 }}
      </td>
      <td class="text-center">
        {{ item.name }}
      </td>
      <td class="text-center">
        {{ item.description }}
      </td>
      <td class="text-center">
        {{ formatDate(item.date) }}
      </td>
    </tr>
  </tbody>
</VTable>
</template>