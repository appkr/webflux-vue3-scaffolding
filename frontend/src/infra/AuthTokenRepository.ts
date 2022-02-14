import { AuthToken } from '@/model/AuthToken'
import { JWT } from '@/model/JWT'

export class AuthTokenRepository {
  static readonly KEY = 'authToken'

  public save(authToken: AuthToken): void {
    localStorage.setItem(AuthTokenRepository.KEY, JSON.stringify(authToken))
  }

  public get(): AuthToken {
    const value = localStorage.getItem(AuthTokenRepository.KEY)
    if (!value) {
      return new AuthToken()
    }

    return JSON.parse(value) as AuthToken
  }

  public remove(): void {
    localStorage.removeItem(AuthTokenRepository.KEY)
  }

  // NOTE: js/ts는 JSON에서 역직렬화하면 인스턴스 메서드를 사용할 수 없어서 데이터를 접근하는 Helper를 여기에 작성함

  public getTokenType(): string {
    return this.get().tokenType ?? 'bearer'
  }

  public getAccessTokenString(): string {
    return this.get().accessTokenString ?? ''
  }

  public getRefreshTokenString(): string {
    return this.get().refreshTokenString ?? ''
  }

  public getUsername(): string {
    return this.get().accessTokenJwt?.username ?? 'unknown'
  }

  public isExpired(): boolean {
    const now = new Date().getTime()
    const exp = this.get().accessTokenJwt?.exp
    if (!exp) {
      return true
    }

    return (exp - now) <= JWT.GRACE_PERIOD_MILLIS
  }
}
