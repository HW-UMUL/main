<script setup>
import AnalyticsAward from '@/views/dashboard/AnalyticsAward.vue';
// import post from '@/views/'

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

<template>
  <VRow>
    <VCol
      cols="12"
      md="8"
      class="mb-4"
    >
        <AnalyticsAward style="margin-bottom: 20px;"/>
        <AnalyticsAward style="margin-bottom: 20px;"/>
        <AnalyticsAward style="margin-bottom: 20px;"/>
        <AnalyticsAward style="margin-bottom: 20px;"/>
    </VCol>  
    <VCol
      cols="12"
      md="4"
    >
        <AnalyticsAward />
    </VCol>  

  </VRow>
</template>
