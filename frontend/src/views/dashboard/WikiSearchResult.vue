<script>
import axios from "axios";

const state = reactive({
  items: []
})
axios.get("http://localhost:8080/api/wiki/readall", {
  headers: {
    'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiYXV0aCI6IlJPTEVfQURNSU4sUk9MRV9VU0VSIiwiZXhwIjoxNzk2MjY1NTM1fQ.1_UIoRPlOCIlwjt5YZCDpz0-tOMQ-9UfB4ghW6MRlGmCBzqkJJTGDdW6pO1rSDsCw6lOtq1l3T266iccihzOJA'
  }
}).then((res) => {
    console.log(res.data[0])
    state.items = res;
    console.log(state.items.data[0])
})

</script>
</script>

<template>
    
</template>
