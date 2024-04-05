import App from '@/App.vue';
import { registerPlugins } from '@core/utils/plugins';
import { createApp } from 'vue';
import VueCookies from 'vue-cookies';

//fort-awesome
import { library } from '@fortawesome/fontawesome-svg-core';
import { faUserSecret } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

import { faThumbsUp as farThumsUp } from '@fortawesome/free-regular-svg-icons';
import { faThumbsUp as fasThumsUp } from '@fortawesome/free-solid-svg-icons';

import { faStar as fasStar } from '@fortawesome/free-regular-svg-icons';
import { faStar as farStar } from '@fortawesome/free-solid-svg-icons';

// Styles
import '@core/scss/template/index.scss';
import '@layouts/styles/index.scss';

// Create vue app
const app = createApp(App)

app.use(VueCookies)


app.provide("app", app)

//const serverAddress = "localhost:8080"
//const serverAddress = "118.36.173.120:11216"
//const serverAddress = "118.36.173.120:11216"
const serverAddress = "minicrane.store:8080"
const profileAddress = "http://" + serverAddress + "/profile/"

app.provide('serverAddress', serverAddress)
app.provide('profileAddress', profileAddress)
registerPlugins(app)

library.add(faUserSecret, farThumsUp, fasThumsUp, farStar, fasStar)

app.component('font-awesome-icon', FontAwesomeIcon)

// Mount vue app
app.mount('#app')
