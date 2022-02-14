import { UaaToken } from '@/infra/api'
import { JWT } from '@/model/JWT'

export class AuthToken {
  readonly accessTokenString?: string;
  readonly refreshTokenString?: string;
  readonly tokenType?: string;
  readonly expiresIn?: number;
  readonly scope?: string;
  readonly iat?: number;
  readonly jti?: string;
  readonly accessTokenJwt?: JWT;
  readonly refreshTokenJwt?: JWT;

  constructor(uaaToken?: UaaToken) {
    this.accessTokenString = uaaToken?.access_token
    this.refreshTokenString = uaaToken?.refresh_token
    this.tokenType = uaaToken?.token_type
    this.expiresIn = uaaToken?.expires_in
    this.scope = uaaToken?.scope
    this.iat = uaaToken?.iat
    this.jti = uaaToken?.jti

    if (uaaToken?.access_token) {
      this.accessTokenJwt = new JWT(uaaToken.access_token)
    }

    if (uaaToken?.refresh_token) {
      this.refreshTokenJwt = new JWT(uaaToken.refresh_token)
    }
  }
}
