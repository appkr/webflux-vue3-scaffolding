import { Configuration, ExampleApiFactory, ExampleList } from '@/service'
import { Ref, ref } from 'vue'

const configuration = new Configuration({ basePath: 'http://localhost:8090' })
const exampleApiClient = ExampleApiFactory(configuration)

const exampleList = ref<ExampleList>({})
const problem = ref<string|null>(null)
const listExamples = () => {
  exampleApiClient.listExamples()
    .then((res) => {
      exampleList.value = res.data
    })
    .catch(err => {
      problem.value = err.response.data
    })

  return { exampleList, problem }
}

const createExample = (name: Ref) => {
  return exampleApiClient.createExample({ name: name.value })
    .then(res => {
      listExamples()
      name.value = ''
    })
    .catch(err => {
      problem.value = err.response.data
    })
}

export default {
  listExamples,
  createExample
}
