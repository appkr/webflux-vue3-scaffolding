<template>
  <div class="control">
    <label :for="id">{{ labelText }}</label>
    <input type="password" class="input"
           :id="id"
           :value="modelValue"
           @change="$emit('update:modelValue', $event.target.value)"
    />
  </div>
</template>

<script setup lang="ts">
import { defineProps } from 'vue'
import { ProblemDetails } from '@/infra/api'

const props = defineProps<{
  id: string,
  labelText: string,
  modelValue: string,
  problem: ProblemDetails
}>()
</script>
