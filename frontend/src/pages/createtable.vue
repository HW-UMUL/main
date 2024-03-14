<script setup>
import { VCol, VRow, VTextField, VTextarea } from 'vuetify/lib/components/index.mjs';


const table = ref({
  name: '',
  desc: ''
})

async function createTable(){

  const formData = {
    name: table.value.name,
    desc: table.value.desc
  }

  const response = await fetch(
      `http://localhost:8080/api/table/create`,
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
        credentials: 'include'
      }
  )

  if(!response.ok) {
    alert("실패!")
  } else{
    window.location.href = 'http://localhost:5173/table'
  }
}

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
            <form @submit.prevent="createTable()">
            <p>Table 생성</p>
                <VCol
                >
                  <VTextField
                  id="name"
                  v-model="table.name"
                  placeholder="조직 이름"
                  label="조직 이름"
                  />
                </VCol>
                
                <VCol
                >
                  <VTextarea
                  id="desc"
                  v-model="table.desc"
                  placeholder="설명"
                  label="설명"
                  />
                </VCol>

              <VCol cols="12">
                <VBtn
                  type="submit"
                  class="me-5"
                >
                  Submit
                </VBtn>

                <VBtn
                  color="secondary"
                  type="reset"
                  variant="outlined"
                >
                  Reset
                </VBtn>
              </VCol>
            </form>
          </div>
        </VCardText>
      </VCard>
    
    </VCol>  

  </VRow>
</template>
