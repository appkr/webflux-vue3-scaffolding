export class JWT {
  static GRACE_PERIOD_MILLIS = 30 * 1000 // 30초

  readonly username?: string;
  readonly scope: string[] = [];
  readonly ati?: string
  readonly exp?: number;
  readonly iat?: number;
  readonly authorities: string[] = [];
  readonly jti?: string;
  readonly clientId?: string;

  constructor(token: string) {
    const base64Payload: string = token.split('.')[1]
    const jsonPayload = atob(base64Payload)
    const parsed = JSON.parse(jsonPayload)

    this.username = parsed?.user_name
    this.scope = parsed?.scope
    this.ati = parsed?.ati
    this.exp = parsed?.exp * 1000
    this.iat = parsed?.iat * 1000
    this.authorities = parsed?.authorities
    this.jti = parsed?.jti
    this.clientId = parsed?.client_id
  }

  getUsername(): string {
    return this.username ?? 'unknown'
  }

  getExpiresAt(): number {
    return this.exp! - JWT.GRACE_PERIOD_MILLIS
  }
}
