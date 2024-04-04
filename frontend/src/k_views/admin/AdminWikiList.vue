<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router'

// 년-월-일
const formatDate = function(value) {
    const date = new Date(value);
    const year = date.getFullYear().toString().slice(-2);
    const month = (date.getMonth() + 1);
    const day = date.getDate();

    return `${year}-${month}-${day}`;
}

const sortDirection = ref('desc')
const sortBy = ref('id')

function togglesort(sortType) {
  if (sortBy.value === sortType) {
    sortDirection.value = sortDirection.value === 'asc' ? 'desc' : 'asc';
  } else {
    sortBy.value = sortType;
    sortDirection.value = 'asc';
  }
  issort();
}

function issort() {
  sortBy.value === 'id';
  sortBy.value === 'like';
  sortBy.value === 'star';
  sortBy.value === 'date';

  sortedWiki.value.sort((a, b) => {
    const compareResult = sortDirection.value === 'asc' ? -1 : 1;
    switch (sortBy.value) {
        case 'id':
            return compareResult * (a.id - b.id);
        case 'title':
            return compareResult * (a.title.localeCompare(b.title))
        case 'category':
            return compareResult * (a.category.name.localeCompare(b.category.name))
        case 'like':
            return compareResult * (a.likes - b.likes);
        case 'star':
            return compareResult * (a.stars - b.stars);
        case 'date':
            return compareResult * (new Date(a.date) - new Date(b.date));
        default:
            return 0;
    }
  })
}

const serverAddress = inject('serverAddress')
const auth = inject('auth')

const sortedWiki = ref([])
const wiki = ref([])

async function getWiki() {
  try {
    const response = await fetch(
      `http://${serverAddress}/api/wiki/read`,
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${auth}`
        },
        credentials: 'include'
      }
    );
    if (!response.ok) {
      console.error(error)
    }
    const beforeWiki = await response.json()
    
    wiki.value = await Promise.all(beforeWiki.map(async (wiki) => {
      const likes = await getLikes(wiki.id)
      const stars = await getStars(wiki.id)
      return { ...wiki, likes: likes, stars: stars }
    }))

    sortedWiki.value = [...wiki.value]; // 정렬된 배열
    issort()
  } catch (error) {
    console.error(error)
  }
}

getWiki()

async function getLikes(wikiId){
  
  const response = await fetch(
    `http://localhost:8080/api/wikilike/read/${wikiId}`,
    {
        method: 'GET',
        headers: {
        'Content-Type': 'application/json',
        },
        credentials: 'include'
    }
  )

  if(!response.ok) {
    console.error(error)
  } else{
    const likes = await response.json()
    return likes
  }
}

async function getStars(wikiId) {
  
    const response = await fetch(`http://${serverAddress}/api/wikistar/read/${wikiId}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${auth}`
      },
      credentials: 'include'
    })

    if (!response.ok) {
      console.error(error)
    } else{
    const stars = await response.json()
    return stars
  }
}

const router = useRouter()
function readWiki(id){
  router.push({
    path: `/readwiki/${id}`
  })
}


</script>

<template>
<div
  style="font-size: small;
  margin: 10px;">
  total : {{ wiki.length }}
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
        Title
        <VIcon
          size="12pt"
          :icon="sortBy === 'title' ? (sortDirection === 'asc' ? 'ri-arrow-up-s-line' : 'ri-arrow-down-s-line') : 'ri-subtract-line'"
          @click="togglesort('title')" />
      </th>
      <th class="text-center">
        Category
        <VIcon
          size="12pt"
          :icon="sortBy === 'category' ? (sortDirection === 'asc' ? 'ri-arrow-up-s-line' : 'ri-arrow-down-s-line') : 'ri-subtract-line'"
          @click="togglesort('category')" />
      </th>
      <th class="text-center">
        Like
        <VIcon
          size="12pt"
          :icon="sortBy === 'like' ? (sortDirection === 'asc' ? 'ri-arrow-up-s-line' : 'ri-arrow-down-s-line') : 'ri-subtract-line'"
          @click="togglesort('like')" />
      </th>
      <th class="text-center">
        Star
        <VIcon
          size="12pt"
          :icon="sortBy === 'star' ? (sortDirection === 'asc' ? 'ri-arrow-up-s-line' : 'ri-arrow-down-s-line') : 'ri-subtract-line'"
          @click="togglesort('star')" />
      </th>
      <th class="text-center">
        Date
        <VIcon
         size="12pt" 
         :icon="sortBy === 'date' ? (sortDirection === 'asc' ? 'ri-arrow-up-s-line' : 'ri-arrow-down-s-line') : 'ri-subtract-line'"
         @click="togglesort('date')" />
      </th>
    </tr>
  </thead>

  <tbody>
    <tr v-for="(item, index) in sortedWiki" :key="index">
      <td class="text-center" style="width: 60pt">
        {{ item.id }}
      </td>
      <td>
        <VIconBtn 
          class="text-center"
          style="cursor: pointer; color: #8C57FF;"
          @click="readWiki(item.id)">
          {{ item.title }}
        </VIconBtn>
      </td>
      <td class="text-center" style="width: 100pt">
        {{ item.category.name }}
      </td>
      <td class="text-center" style="width: 100pt">
        {{ item.likes }}
      </td>
      <td class="text-center" style="width: 100pt">
        {{ item.stars }}
      </td>
      <td class="text-center" style="width: 150pt">
      {{ formatDate(item.date) }}
      </td>
    </tr>
  </tbody>
</VTable>
</template>
