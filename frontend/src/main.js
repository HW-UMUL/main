import App from '@/App.vue';
import store from '@/api/store';

import { registerPlugins } from '@core/utils/plugins';
import { createApp } from 'vue';

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

// Register plugins
registerPlugins(app)

// Mount vue app
app.use(store);
app.mount('#app')

library.add(faUserSecret, farThumsUp, fasThumsUp, farStar, fasStar)

app.component('font-awesome-icon', FontAwesomeIcon)
