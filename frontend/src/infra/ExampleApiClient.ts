import { Configuration, Example, ExampleApi, ExampleList } from '@/infra/api/'
import { AuthTokenRepository } from '@/infra/AuthTokenRepository'

export class ExampleApiClient {
  private readonly apiClient: ExampleApi
  private readonly authTokenRepository = new AuthTokenRepository()

  constructor() {
    const tokenType = this.authTokenRepository.getTokenType()
    const accessToken = this.authTokenRepository.getAccessTokenString()
    const config = new Configuration({
      basePath: 'http://localhost:8090',
      apiKey: `${tokenType} ${accessToken}`
    })
    this.apiClient = new ExampleApi(config)
  }

  public createExample(example?: Example): Promise<Example> {
    return new Promise((resolve, reject) => {
      this.apiClient.createExample(example)
        .then(res => {
          resolve(res.data)
        })
        .catch(err => {
          // TODO 토큰 만료시 갱신
          reject(err.response.data)
        })
    })
  }

  public listExamples(): Promise<ExampleList> {
    return new Promise((resolve, reject) => {
      this.apiClient.listExamples()
        .then(res => {
          resolve(res.data)
        })
        .catch(err => {
          // TODO 토큰 만료시 갱신
          reject(err.response.data)
        })
    })
  }
}
