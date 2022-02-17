import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import { UserState } from '@/model/UserState'

const userStateJson = localStorage.getItem('userState')
if (userStateJson) {
  // LocalStorage에 저장된 UserState를 꺼내서 객체로 재생
  const { username, accessToken, refreshToken, expiresAt } = JSON.parse(userStateJson) as UserState
  const userState = new UserState(username, accessToken, refreshToken, expiresAt)
  setInterval(() => {
    if (!userState.isExpired()) {
      // UserState가 유효하면 VuexStore 갱신
      store.state.userState = userState
    } else {
      // UserState가 만료되었다면, LocalStorage를 초기화하고 토큰 갱신 요청
      localStorage.removeItem('userState')
      store.dispatch('refresh')
    }
  }, 10000)
}

createApp(App).use(store).use(router).mount('#app')
