<script setup>
import AdminUserList from '@/k_views/admin/AdminUserList.vue';
</script>

<template>
<VRow class="mb-2">
  <VCol
    cols="12"
    md="12"
  >
    <VCard>
      <VCardTitle style="margin:10px">User</VCardTitle>
      <div>
        <AdminUserList />
      </div>
    </VCard>
  </VCol>
</VROw>
</template>