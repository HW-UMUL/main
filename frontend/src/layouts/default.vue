<script setup>
console.log('i111')
import DefaultLayoutWithVerticalNav from './components/DefaultLayoutWithVerticalNav.vue'
console.log('i2w2')
</script>

<template>
  <DefaultLayoutWithVerticalNav>
    <RouterView />
  </DefaultLayoutWithVerticalNav>
</template>

<style lang="scss">
// As we are using `layouts` plugin we need its styles to be imported
@use "@layouts/styles/default-layout";
</style>
