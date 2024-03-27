import { createStore } from 'vuex';

export default createStore({

  state: {
    isLoggedIn: false,
    authToken: ''
  },
  mutations: {
    login(state, authToken) {
      state.isLoggedIn = true;
      state.authToken = authToken;
    },
    logout(state) {
      state.isLoggedIn = false;
      state.authToken = '';
    }
  },
  actions: {
    // 필요한 경우 추가적인 액션 정의 가능
  }
});
