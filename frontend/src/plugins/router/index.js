import store from '@/api/store'; // Vuex 스토어 설정
import { createRouter, createWebHistory } from 'vue-router';
import { routes } from './routes';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

export default function (app) {
  app.use(router)
  app.use(store)
}

export { router, store };

