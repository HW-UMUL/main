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
  <VCard class="position-relative">
    <VCardText class>
      <h5 class="text-h5"> 최신순 </h5>
    </VCardText>

    <VCardText>
      <VCol>
        <div v-for="(item, index) in sortedDate(posts).slice(0,5)" :key="index" style="display: flex; justify-content: space-between;">
          <span style="margin-right: auto;"> {{ item.title }} </span>
          <span style="margin-left: auto;"> {{ formatDate(item.date) }}</span>
        </div>
      </VCol>
    </VCardText>
  </VCard>
  </template>
  