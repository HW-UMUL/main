import { createRouter, createWebHistory } from 'vue-router'
import { routes } from './routes'
import VueCookies from 'vue-cookies'
//import socket from 'vue3-websocket'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})




export default function (app) {
  app.use(VueCookies)

  app.provide('router', router)
  
  router.beforeEach((to, from, next) => {
    const isAuthenticated = $cookies.get('jwtToken')
    app.config.globalProperties.isAuthenticated = isAuthenticated; // 앱 전역에서 사용할 수 있도록 설정
  
    if (!(to.path === '/login' || to.path === '/register') && !isAuthenticated) { // 로그인 화면이 아니고, 인증되지 않은 경우
      next('/login'); // 로그인 화면으로 이동
    } else {

//    app.use(socket, 'ws://localhost:8080/ws')

    

      next(); // 그 외의 경우에는 이동을 허용
    }
  });
  
  app.use(router)
}
export { router }
