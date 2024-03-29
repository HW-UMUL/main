<script setup>
import MyWikiList from '@/views/wiki/MyWikiList.vue';
import ViewRecentWiki from '@/d_dashboard/ViewRecentWiki.vue'
import ViewWikiLikeRank from '@/d_dashboard/ViewWikiLikeRank.vue'
import ViewWikiStarRank from '@/d_dashboard/ViewWikiStarRank.vue'


</script>

<template>
  <VRow>
    <VCol
      cols="12"
      md="8"
      class="mb-4"
    >
    
    <MyWikiList/>
    </VCol>  
    <VCol
      cols="12"
      md="4"
    >
      <ViewRecentWiki style="margin-bottom: 20px;"/>
      <ViewWikiLikeRank style="margin-bottom: 20px;"/>
      <ViewWikiStarRank style="margin-bottom: 20px;"/>
    </VCol>  

  </VRow>
</template>
