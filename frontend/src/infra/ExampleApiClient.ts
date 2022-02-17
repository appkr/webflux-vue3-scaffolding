import { Configuration, Example, ExampleApi, ExampleList } from '@/infra/api/'
import store from '@/store'

export class ExampleApiClient {
  private readonly apiClient: ExampleApi

  constructor() {
    const accessToken = store.state.userState?.accessToken ?? null
    const config = new Configuration({
      basePath: 'http://localhost:8090',
      apiKey: `bearer ${accessToken}`
    })
    this.apiClient = new ExampleApi(config)
  }

  createExample(example?: Example): Promise<Example> {
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

  listExamples(): Promise<ExampleList> {
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
