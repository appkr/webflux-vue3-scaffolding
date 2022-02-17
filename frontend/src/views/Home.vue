<template>
  <form @submit.prevent="onSubmit" @keydown="problem=null">
    <TextField id="name" labelText="Name" v-model="name" :problem="problem"/>
    <Button type="submit" buttonText="Submit" :submitting="submitting"/>
  </form>
  <p class="title mt-6">Visited Names</p>
  <ul>
    <li v-for="example in exampleList.data" :key="example.id">
      {{ example.id }} {{ example.name }}
    </li>
  </ul>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import Button from '@/components/Button.vue'
import TextField from '@/components/TextField.vue'
import { ExampleApiClient } from '@/infra/ExampleApiClient'
import { ExampleList, ProblemDetails } from '@/infra/api'

const exampleList = ref<ExampleList>({ data: [] })
const name = ref<string>('')
const submitting = ref(false)
const problem = ref<ProblemDetails>()
const exampleApiClient = new ExampleApiClient()

const onSubmit = () => {
  submitting.value = true
  exampleApiClient.createExample({ name: name.value })
    .then(() => {
      name.value = ''
      load()
    })
    .catch(err => {
      problem.value = err
    })
    .finally(() => {
      submitting.value = false
    })
}

const load = () => {
  exampleApiClient.listExamples()
    .then(res => {
      exampleList.value = res
    })
    .catch(err => {
      problem.value = err
    })
}

onMounted(load)
</script>
