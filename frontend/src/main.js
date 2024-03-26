import { createApp } from 'vue'
import App from '@/App.vue'
import { registerPlugins } from '@core/utils/plugins'
import socket from '/test/index.js'
import VueCookies from 'vue-cookies'

// Styles
import '@core/scss/template/index.scss'
import '@layouts/styles/index.scss'

// Create vue app
const app = createApp(App)

app.use(VueCookies)


app.provide("app", app)

//const serverAddress = "localhost:8080"

//const serverAddress = "172.30.1.56:8088"

const serverAddress = "ec2-13-209-98-152.ap-northeast-2.compute.amazonaws.com:8080"

//app.use(socket, `ws://${serverAddress}/ws`)

//app.use(socket, 'ws://localhost:8080/ws')
//app.use(socket, `ws://${serverAddress}/ws`)

//app.provide('socket', socket)
app.provide('serverAddress', serverAddress)
//const socket = inject('socket')
//socket.install.reconnect = false

// Register plugins
registerPlugins(app)

// Mount vue app
app.mount('#app')
