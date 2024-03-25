<script setup>
import { onMounted, ref } from 'vue';

const props = defineProps({
    poststar: Object
})

const star = ref([])

async function getStars(){
  
  const response = await fetch(
    `http://localhost:8080/api/poststar/read/${props.poststar.id}`,
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
    star.value = await response.json()
  }
}

onMounted(getStars)

</script>

<template>
    <span>
        {{ star }}
    </span>
</template>
  