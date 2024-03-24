<script setup>
import DefaultLayoutWithVerticalNav from './components/MyPageLayoutWithVerticalNav.vue'
import socketModule from '/test/index.js'


const serverAddress = inject('serverAddress')
const auth = inject('auth')

// websocket

socketModule.connectionString = `ws://${serverAddress}/ws/notifier`

socketModule.setConnectionString(`ws://${serverAddress}/ws/notifier`)
socketModule.connect()

socketModule.onOpen(() => {
  const formData = { auth: auth }
  socketModule.socket.value.send(JSON.stringify(formData))
})

socketModule.onMessage(message => {
    const jsonData = JSON.parse(message.data)
    console.log(jsonData)


        // Parse any JSON previously stored in allEntries
    var existingEntries = JSON.parse(localStorage.getItem("allEntries"));
    if(existingEntries == null) existingEntries = [];
    var entry = {
        "title": jsonData.tableName,
        "text": jsonData.postTitle
    };
    localStorage.setItem("entry", JSON.stringify(entry));
    // Save allEntries back to local storage
    existingEntries.push(entry);
    localStorage.setItem("allEntries", JSON.stringify(existingEntries));
})

socketModule.onClose(() => {
  socketModule.connect()
})


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
