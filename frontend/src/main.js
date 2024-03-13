import { createApp } from 'vue'
import App from '@/App.vue'
import { registerPlugins } from '@core/utils/plugins'
//import socket from 'vue3-websocket'
import socket from '/test/index.js'

// Styles
import '@core/scss/template/index.scss'
import '@layouts/styles/index.scss'

// Create vue app
const app = createApp(App)

app.use(socket, 'ws://localhost:8080/ws')

app.provide('socket', socket)

//const socket = inject('socket')
//socket.install.reconnect = false

// Register plugins
registerPlugins(app)

// Mount vue app
app.mount('#app')
