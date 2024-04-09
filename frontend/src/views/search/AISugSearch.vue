<script setup>
import { ref } from 'vue';

const items = ref([
  { title: 'Item 1', description: 'Description of Item 1' },
  { title: 'Item 2', description: 'Description of Item 2' },
  { title: 'Item 3', description: 'Description of Item 3' }
]);
</script>


<template>
    <v-card
    class="mx-auto"
    max-width="300"
    >
    <v-list :items="items"></v-list>
    </v-card>
</template>