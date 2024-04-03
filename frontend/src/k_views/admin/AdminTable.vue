<script setup>

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
<VRow class="mb-2">
  <VCol
    cols="12"
    md="12"
  >
    <VCard>
      <VCardTitle style="margin:10px">Table</VCardTitle>
      <div v-for="(item, index) in tables" :key="index">
        {{ item }}
      </div>
    </VCard>
  </VCol>
</VROw>
</template>