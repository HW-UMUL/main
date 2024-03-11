<script setup>
import AnalyticsTotalProfitLineCharts from '@/views/dashboard/AnalyticsTotalProfitLineCharts.vue';
import AnalyticsWeeklyOverview from '@/views/dashboard/AnalyticsWeeklyOverview.vue';
import CardStatisticsVertical from '@core/components/cards/CardStatisticsVertical.vue';

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
        <VCol cols="3" sm="12">
          <AnalyticsWeeklyOverview />
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
          <AnalyticsTotalProfitLineCharts />
        </VCol>

        <VCol
          cols="5"
          sm="12"
        >
          <CardStatisticsVertical v-bind="totalProfit" />
        </VCol>

        <VCol
          cols="5"
          sm="12"
        >
          <CardStatisticsVertical v-bind="newProject" />
        </VCol>
      </VRow>
    </VCol>
  </VRow>
</template>
