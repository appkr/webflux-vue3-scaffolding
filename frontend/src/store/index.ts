import { createStore } from 'vuex'
import { UserState } from '@/model/UserState'
import { AuthApi, Configuration, UaaToken } from '@/infra/api'
import { JWT } from '@/model/JWT'
import { AxiosResponse } from 'axios'

const config = new Configuration({ basePath: 'http://localhost:8090' })
const authApi = new AuthApi(config)
const createUserStateFrom = (res: AxiosResponse<UaaToken>) => {
  const accessTokenJwt = new JWT(res.data.access_token!)

  const userState = new UserState(
    accessTokenJwt.getUsername(),
    res.data.access_token!,
    res.data.refresh_token!,
    accessTokenJwt.getExpiresAt()
  )

  return userState
}

export default createStore({
  state: {
    userState: null as UserState|null
  },
  mutations: {
    setUserState(state, userState: UserState|null) {
      localStorage.setItem('userState', JSON.stringify(userState))
      state.userState = userState
    }
  },
  actions: {
    async login(context, { username, password }) {
      const res = await authApi.login({ username, password })
      if (res.status === 200) {
        context.commit('setUserState', createUserStateFrom(res))
      }
      return res
    },
    async refresh(context) {
      const userState = context.state.userState
      if (userState) {
        const res = await authApi.refresh({ refresh_token: context.state.userState!.refreshToken })
        if (res.status === 200) {
          console.log('액세스토큰을 갱신했습니다')
          context.commit('setUserState', createUserStateFrom(res))
        }
        return res
      }
    }
  }
})
