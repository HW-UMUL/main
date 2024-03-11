<script setup>

const post = ref({
  title: '',
  content: '',
  category: '',
  tag: ''
})

async function writePost(){

  const formData = {
    title: post.value.title,
    content: post.value.content,
    category: post.value.category,
    tag: post.value.tag
  }

  const response = await fetch(
      `http://localhost:8080/api/post/create`,
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
    window.location.href = 'http://localhost:5173/'
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
    <div>
        <form @submit.prevent="writePost()">
            <input type="text" v-model="post.title"></input>
            <input type="text" v-model="post.content"></input>
            <input type="text" v-model="post.category"></input>
            <input type="text" v-model="post.tag"></input>
            <input type="submit">submit</input>
        </form>
    </div>
    </VCol>  

  </VRow>
</template>
