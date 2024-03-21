<script setup>
import { formatDateToMonthShort } from '@/@core/utils/formatters';
import index from 'vue-prism-component';
import { VCard, VCardItem, VCardText, VCardTitle, VCol, VDivider, VIcon, VTextField } from 'vuetify/lib/components/index.mjs';

// 년-월-일
const formatDate = function(value) {
    const date = new Date(value);
    const year = date.getFullYear();
    const month = (date.getMonth() + 1);
    const day = date.getDate();
    const hour = date.getHours();
    const min = date.getMinutes();
    const sec = date.getSeconds();
    
    return `${year}-${month}-${day}`;
}

const props = defineProps({
    post: Object
})

const like = ref([])
const star = ref([])
const posts = ref([])

async function getPosts(){

  const response = await fetch(
      `http://localhost:8080/api/post/read`,
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
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

function sortedDate(value) {
  const sortedValue = value.slice().sort(function(a,b) {
    return new Date(b.date).getTime() - new Date(a.date).getTime()
  })
  return sortedValue
}

</script>

<template>
<VTable>  
    <thead>
      <tr>
        <th class="text-center">
          Title
        </th>
        <th class="text-uppercase text-center">
          Date
        </th>
      </tr>
    </thead>

    <tbody>
      <tr
        v-for="(item, index) in sortedDate(posts).slice(0,5)" 
        :key="index"
      >
        <td class="text-center">
          {{ item.title }}
        </td>
        <td class="text-center">
          {{ formatDate(item.date) }}
        </td>
      </tr>
    </tbody>
  </VTable>
  <!-- <VCard class="position-relative">

    <VCardText>
      <VCol>
        <div class="mb-2" style="display: flex; justify-content: space-between;">
          <span>제목</span>
          <span class="mr-5">날짜</span>
        </div>
        <VDivider class="mb-2"/>
        <div v-for="(item, index) in sortedDate(posts).slice(0,5)" :key="index" style="display: flex; justify-content: space-between;">
          <span style="margin-right: auto;"> {{ item.title }} </span>
          <span style="margin-left: auto;"> {{ formatDate(item.date) }}</span>
        </div>
      </VCol>
    </VCardText>
  </VCard> -->

  </template>
  