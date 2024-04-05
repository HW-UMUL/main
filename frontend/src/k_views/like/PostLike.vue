<script setup>
import { onMounted, ref } from 'vue';

const serverAddress = inject('serverAddress')
const auth = inject('auth')

const props = defineProps({
    postlike: Object
})

const like = ref([])

async function getLikes(){
  
  const response = await fetch(
    `http://${serverAddress}/api/postlike/read/${props.postlike.id}`,
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
    like.value = await response.json()
  }
}

onMounted(getLikes)

</script>

<template>
    <span>
        {{ like }}
    </span>
</template>
  