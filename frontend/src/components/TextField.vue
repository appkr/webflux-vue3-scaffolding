<template>
  <div class="control">
    <label :for="id">{{ labelText }}</label>
    <input type="text" class="input"
           :id="id"
           :class="{'is-danger': problem}"
           :value="modelValue"
           @change="$emit('update:modelValue', $event.target.value)"
    />
  </div>
  <div v-show="errorMessage" class="is-size-7 has-text-danger">{{ errorMessage }}</div>
</template>

<script setup lang="ts">
import { computed, defineProps } from 'vue'
import { ProblemDetails } from '@/infra/api'

const props = defineProps<{
  id: string,
  labelText: string,
  modelValue: string,
  problem: ProblemDetails
}>()

const errorMessage = computed(() => {
  if (props.problem) {
    const fieldErrors = props.problem.violations.filter(violation => violation.field === props.id)
    if (fieldErrors.length > 0) {
      return fieldErrors[0].message
    }
  }

  return null
})
</script>
