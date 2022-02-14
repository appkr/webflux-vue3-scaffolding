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
import { defineComponent, ref } from 'vue'
import TextField from '@/components/TextField.vue'
import SubmitButton from '@/components/SubmitButton.vue'
import exampleApiClient from '@/composables/exampleApiClient'

export default defineComponent({
  components: { SubmitButton, TextField },
  setup (): Record<string, unknown> {
    const { exampleList, problem } = exampleApiClient.listExamples()

    const name = ref<string>('')
    const submitting = ref<boolean>(false)

    const onSubmit = () => {
      submitting.value = true
      exampleApiClient.createExample(name)
        .finally(() => {
          submitting.value = false
        })
    }

    return {
      exampleList,
      name,
      onSubmit,
      submitting,
      problem
    }
  }
})
</script>
