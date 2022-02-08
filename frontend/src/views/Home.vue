<template>
  <form @submit.prevent="onSubmit" @keydown="errors=null">
    <div class="field is-grouped">
      <p class="control is-expanded">
        <input type="text" class="input" :class="{'is-danger': errors}" placeholder="Leave your name here" v-model="newName"/>
      </p>
      <p class="control">
        <button type="submit" class="button is-info" :disabled="disableSubmit">
          Submit
        </button>
      </p>
    </div>
    <div v-show="errors" class="is-size-7 has-text-danger">{{ errors }}</div>
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

export default {
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
    const newName = ref<string>('')
    const disableSubmit = ref<boolean>(false)
    const errors = ref<string|null>(null)

    const onSubmit = () => {
      disableSubmit.value = true
      apiClient.createExample({ name: newName.value })
        .then(response => {
          listExamples()
          newName.value = ''
        }).catch(err => {
          errors.value = err.response.data.violations[0].message
        }).finally(() => {
          disableSubmit.value = false
        })
    }

    onMounted(listExamples)

    return {
      exampleList,
      newName,
      onSubmit,
      disableSubmit,
      errors
    }
  }
}
</script>
