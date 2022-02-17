export class UserState {
  readonly username: string
  readonly accessToken: string
  readonly refreshToken: string
  readonly expiresAt: number

  constructor(username: string, accessToken: string, refreshToken: string, expiresAt: number) {
    this.username = username
    this.accessToken = accessToken
    this.refreshToken = refreshToken
    this.expiresAt = expiresAt
  }

  isExpired(): boolean {
    return new Date().getTime() - this.expiresAt >= 0
  }

  isLoggedIn(): boolean {
    return !this.isExpired()
  }
}
