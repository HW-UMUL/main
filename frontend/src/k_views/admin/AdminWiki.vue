<script setup>
import AdminWikiList from './AdminWikiList.vue';

</script>

<template>
<VRow class="mb-2">
  <VCol
    cols="12"
    md="12"
  >
    <VCard>
      <VCardTitle style="margin:10px">Wiki</VCardTitle>
      <div>
        <AdminWikiList />
      </div>
    </VCard>
  </VCol>
</VROw>
</template>