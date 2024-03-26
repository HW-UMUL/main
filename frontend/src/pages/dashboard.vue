<script setup>
import ViewAllWiki from '@/views/dashboard/ViewAllWiki.vue'
import ViewRecentWiki from '@/views/dashboard/ViewRecentWiki.vue'
import ViewWikiLikeRank from '@/views/dashboard/ViewWikiLikeRank.vue'
import ViewWikiStarRank from '@/views/dashboard/ViewWikiStarRank.vue'
const totalProfit = {
  title: 'Total Profit',
  color: 'secondary',
  icon: 'ri-pie-chart-2-line',
  stats: '$25.6k',
  change: 42,
  subtitle: 'Weekly Project',
}

const newProject = {
  title: 'New Project',
  color: 'primary',
  icon: 'ri-file-word-2-line',
  stats: '862',
  change: -18,
  subtitle: 'Yearly Project',
}
</script>

<!-- <script>
import axios from "axios"
export default {
 name: "dashboard",
 setup() {
  axios.get("/api/wiki/read/1").then((res) => {
    console.log(res.data);
  })
 } 
}
</script> -->

<template>
  <VRow class="match-height">
    <VCol
      cols="12"
      md="9"
    >
      <VRow class="match-height">
        <VCol
          cols="3"
          sm="12"
        >
          <ViewAllWiki />
        </VCol>
      </VRow>
    </VCol>

    <VCol
      cols="12"
      md="3"
    >
      <VRow class="match-height">
        <VCol
          cols="5"
          sm="12"
        >
          <ViewWikiLikeRank />
        </VCol>

        <VCol
          cols="5"
          sm="12"
        >
          <ViewWikiStarRank />
        </VCol>

        <VCol
          cols="5"
          sm="12"
        >
          <ViewRecentWiki />
        </VCol>
      </VRow>
    </VCol>
  </VRow>
</template>
