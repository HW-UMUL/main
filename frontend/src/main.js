import { createApp } from 'vue'
import App from '@/App.vue'
import { registerPlugins } from '@core/utils/plugins'
import VueCookies from 'vue-cookies'
// vue3 부터는 아래와 같이 작성함
import { createVuetify } from 'vuetify';
const vuetify = createVuetify();

// Styles
import '@core/scss/template/index.scss'
import '@layouts/styles/index.scss'

// Create vue app
const app = createApp(App)
app.use(VueCookies)

app.use(vuetify);

// Register plugins
registerPlugins(app)

// Mount vue app
app.mount('#app')
