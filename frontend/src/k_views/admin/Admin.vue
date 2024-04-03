<script setup>
import AdminPostLikeSort from '@/k_views/admin/sortlist/AdminPostLikeSort.vue';
import AdminPostStarSort from '@/k_views/admin/sortlist/AdminPostStarSort.vue';
import AdminPostDateSort from '@/k_views/admin/sortlist/AdminPostDateSort.vue';
import AdminViewWikiLikeRank from '@/k_views/admin/sortlist/AdminViewWikiLikeRank.vue';
import AdminViewWikiStarRank from '@/k_views/admin/sortlist/AdminViewWikiStarRank.vue';
import AdminViewRecentWiki from '@/k_views/admin/sortlist/AdminViewRecentWiki.vue';

const navigationTab = ref('like')
const navigationTab2 = ref('like')

const tabItems = [
  'like',
  'star',
  'date',
]

const tabContent = 'admin'

</script>

<template>
<VRow class="mb-2">
  <VCol
    cols="12"
    md="12"
  >
    <VCard style="z-index: 1;">
      <VCardTitle>Post</VCardTitle>
      <VTabs v-model="navigationTab">
          <VTab
              v-for="item in tabItems"
              :key="item"
              :value="item"
          >
          {{ item }}
          </VTab>
      </VTabs>

      <!-- tabs content -->
      <VWindow v-model="navigationTab">
        <VWindowItem
          v-for="item in tabItems"
          :key="item"
          :value="item"
        >
        <template v-if="item=='like'">
          <AdminPostLikeSort />
        </template>
        <template v-if="item=='star'">
          <AdminPostStarSort />
        </template>
        <template v-if="item=='date'">
          <AdminPostDateSort />
        </template>
          <!-- <VCardText>
            <VBtn>Learn More</VBtn>
          </VCardText> -->
        </VWindowItem>
      </VWindow>
    </VCard>
  </VCol>
</VROw>

<VRow class="mb-2">
  <VCol
    cols="12"
    md="12"
  >
    <VCard>
      <VCardTitle>Wiki</VCardTitle>
      <VTabs v-model="navigationTab2">
          <VTab
              v-for="item in tabItems"
              :key="item"
              :value="item"
          >
          {{ item }}
          </VTab>
      </VTabs>

      <!-- tabs content -->
      <VWindow v-model="navigationTab2">
        <VWindowItem
          v-for="item in tabItems"
          :key="item"
          :value="item"
        >
        <template v-if="item=='like'">
          <AdminViewWikiLikeRank/>
        </template>
        <template v-if="item=='star'">
          <AdminViewWikiStarRank />
        </template>
        <template v-if="item=='date'">
          <AdminViewRecentWiki />
        </template>
        </VWindowItem>
      </VWindow>
    </VCard>
  </VCol>
</VROw>
</template>