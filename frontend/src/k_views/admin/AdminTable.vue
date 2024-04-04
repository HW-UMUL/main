<script setup>
import AdminTableList from './AdminTableList.vue';

</script>

<template>
<VRow class="mb-2">
  <VCol
    cols="12"
    md="12"
  >
    <VCard>
      <VCardTitle style="margin:10px">Table</VCardTitle>
      <div>
        <AdminTableList />
      </div>
    </VCard>
  </VCol>
</VROw>
</template>