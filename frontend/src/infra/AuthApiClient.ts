import {
  Configuration,
  UaaToken,
  AuthApi,
  LoginRequest
} from '@/infra/api/'
import { AuthTokenRepository } from '@/infra/AuthTokenRepository'
import { AuthToken } from '@/model/AuthToken'

export class AuthApiClient {
  private readonly apiClient: AuthApi
  private readonly repository: AuthTokenRepository

  constructor() {
    const config = new Configuration({ basePath: 'http://localhost:8090' })
    this.apiClient = new AuthApi(config)
    this.repository = new AuthTokenRepository()
  }

  public login(loginRequest?: LoginRequest): Promise<UaaToken> {
    return new Promise((resolve, reject) => {
      this.apiClient.login(loginRequest)
        .then(res => {
          this.repository.save(new AuthToken(res.data))
          resolve(res.data)
        })
        .catch(err => {
          reject(err.response.data)
        })
    })
  }

  public refresh(): Promise<UaaToken>|null {
    if (!this.repository.isExpired()) {
      return null
    }

    const refreshRequest = {
      refresh_token: this.repository.getRefreshTokenString()
    }

    return new Promise((resolve, reject) => {
      this.apiClient.refresh(refreshRequest)
        .then(res => {
          this.repository.save(new AuthToken(res.data))
          resolve(res.data)
        })
        .catch(err => {
          reject(err.response.data)
        })
    })
  }

  public logout(): boolean {
    this.repository.remove()
    return true
  }

  // TODO: 사용자 조회 API
}
