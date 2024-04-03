<script setup>
const serverAddress = inject('serverAddress')
const auth = inject('auth')

const props = defineProps({
    user: Object
})

const userrole = ref([])
async function getuserrole(){

  const response = await fetch(
      `http://${serverAddress}/api/userrole/${props.user.id}`,
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
    userrole.value = await response.json()
    userrole.value = userrole.value.map(item => item.role.role.substring(5));
  }
}

getuserrole()

</script>

<template>
<div v-for="(item, index) in userrole" :key="index"
    style="cursor: pointer;">
    {{ item }}
</div>
</template>