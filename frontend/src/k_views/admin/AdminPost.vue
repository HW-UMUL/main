<script setup>
import AdminPostList from '@/k_views/admin/AdminPostList.vue';
</script>

<template>
<VRow class="mb-2">
  <VCol
    cols="12"
    md="12"
  >
    <VCard>
      <VCardTitle style="margin:10px">Post</VCardTitle>
      <div>
        <AdminPostList />
      </div>
    </VCard>
  </VCol>
</VROw>
</template>