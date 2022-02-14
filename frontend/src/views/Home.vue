<template>
  <form @submit.prevent="onSubmit" @keydown="problem=null">
    <TextField id="name" labelText="Name" v-model="name" :problem="problem"/>
    <SubmitButton :submitting="submitting"/>
  </form>
  <p class="title mt-6">Visited Names</p>
  <ul>
    <li v-for="example in exampleList.data" :key="example.id">
      {{ example.id }} {{ example.name }}
    </li>
  </ul>
</template>

<script lang="ts">
import { onMounted, ref } from 'vue'
import { Configuration, ExampleApiFactory, ExampleList } from '@/service'
import TextField from '@/components/TextField.vue'
import SubmitButton from '@/components/SubmitButton.vue'

export default {
  components: { SubmitButton, TextField },
  setup (): Record<string, unknown> {
    const configuration = new Configuration({ basePath: 'http://localhost:8090' })
    const apiClient = ExampleApiFactory(configuration)
    const listExamples = () => {
      apiClient.listExamples()
        .then((res) => {
          exampleList.value = res.data
        })
    }

    const exampleList = ref<ExampleList>({})
    const name = ref<string>('')
    const submitting = ref<boolean>(false)
    const problem = ref<string|null>(null)

    const onSubmit = () => {
      submitting.value = true
      apiClient.createExample({ name: name.value })
        .then(() => {
          listExamples()
          name.value = ''
        }).catch(err => {
          problem.value = err.response.data.violations[0].message
        }).finally(() => {
          submitting.value = false
        })
    }

    onMounted(listExamples)

    return {
      exampleList,
      name,
      onSubmit,
      submitting,
      problem
    }
  }
}
</script>
