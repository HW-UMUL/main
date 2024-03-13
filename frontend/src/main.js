import { createApp } from 'vue'
import App from '@/App.vue'
import { registerPlugins } from '@core/utils/plugins'

// Styles
import '@core/scss/template/index.scss'
import '@layouts/styles/index.scss'

// Create vue app
const app = createApp(App)

app.provide('app', app);
//app.use(socket, 'ws://localhost:8080/ws')
// Register plugins
registerPlugins(app)
/*
// 전역 네비게이션 가드 설정
router.beforeEach((to, from, next) => {
    const isAuthenticated = Vue.$cookies.isKey('jwtToken'); // 쿠키의 존재 여부 확인

    app.config.globalProperties.isAuthenticated = isAuthenticated; // 앱 전역에서 사용할 수 있도록 설정

      if (to.name !== 'login' && !isAuthenticated) { // 로그인 화면이 아니고, 인증되지 않은 경우
    next({ name: 'login' }); // 로그인 화면으로 이동
  } else {
    next(); // 그 외의 경우에는 이동을 허용
  }
});
*/
// Mount vue app
app.mount('#app')
