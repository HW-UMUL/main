<script setup>
import AnalyticsAward from '@/views/dashboard/AnalyticsAward.vue';
import Table from '@/views/table/TableSummary.vue';

const auth = inject('auth')
const tables = ref([])


async function getTables(){

  const response = await fetch(
      `http://localhost:8080/api/tableuser/read`,
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
 

getTables()


</script>

<template>
  <VRow>
    <VCol
      cols="12"
      md="8"
      class="mb-4"
    >
    <VBtn 
      to="/createtable"
      >
      Create Table
    </VBtn>

    <div v-for="(item, index) in tables" :key="index">
      <!-- {{ item }}       -->
      <Table :table="item" style="margin-bottom: 20px;"/>
    </div>
        <!-- <wiki style="margin-bottom: 20px;"/>
        <wiki style="margin-bottom: 20px;"/>
        <wiki style="margin-bottom: 20px;"/> -->
    </VCol>  
    <VCol
      cols="12"
      md="4"
    >
        <AnalyticsAward />
    </VCol>  

  </VRow>
</template>
