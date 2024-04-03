<script setup>
import AdminPostLike from '@/k_views/admin/AdminPostList.vue';
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
        <AdminPostLike />
      </div>
    </VCard>
  </VCol>
</VROw>
</template>