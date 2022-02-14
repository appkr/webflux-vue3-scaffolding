<template>
  <form @submit.prevent="onSubmit" @keydown="problem=null">
    <TextField id="username" labelText="Username" v-model="username" :problem="problem"/>
    <PasswordField id="password" labelText="Password" v-model="password" :problem="problem"/>
    <Button type="submit" buttonText="Login" :submitting="submitting"/>
  </form>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
import TextField from '@/components/TextField.vue'
import PasswordField from '@/components/PasswordField.vue'
import Button from '@/components/Button.vue'
import { AuthApiClient } from '@/infra/AuthApiClient'
import { LoginRequest, ProblemDetails } from '@/infra/api/'
import { useRouter } from 'vue-router'

export default defineComponent({
  components: { Button, TextField, PasswordField },
  setup(): Record<string, unknown> {
    const username = ref<string>('')
    const password = ref<string>('')
    const submitting = ref(false)
    const problem = ref<ProblemDetails>()
    const authApiClient = new AuthApiClient()
    const router = useRouter()

    const onSubmit = () => {
      submitting.value = true
      authApiClient.login({ username: username.value, password: password.value })
        .then(() => {
          router.push('/')
        })
        .catch(err => {
          problem.value = err
        })
        .finally(() => {
          submitting.value = false
        })
    }

    return { username, password, submitting, onSubmit, problem }
  }
})
</script>
