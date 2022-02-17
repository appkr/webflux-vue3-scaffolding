<template>
  <form @submit.prevent="onSubmit" @keydown="problem=null">
    <TextField id="username" labelText="Username" v-model="username" :problem="problem"/>
    <PasswordField id="password" labelText="Password" v-model="password" :problem="problem"/>
    <Button type="submit" buttonText="Login" :submitting="submitting"/>
  </form>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import TextField from '@/components/TextField.vue'
import PasswordField from '@/components/PasswordField.vue'
import Button from '@/components/Button.vue'
import { ProblemDetails } from '@/infra/api/'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'

const username = ref<string>('')
const password = ref<string>('')
const submitting = ref(false)
const problem = ref<ProblemDetails>()
const router = useRouter()
const store = useStore()

const onSubmit = () => {
  submitting.value = true
  store.dispatch('login', { username: username.value, password: password.value })
    .then(() => {
      router.push('/')
    })
    .catch(err => {
      console.log(err)
      problem.value = err
    })
    .finally(() => {
      submitting.value = false
    })
}
</script>
