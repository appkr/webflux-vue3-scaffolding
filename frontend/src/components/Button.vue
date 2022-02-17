<template>
  <div class="control has-text-centered mt-4">
    <button :type="type" class="button is-info" :disabled="submitting">
      {{ buttonText }}
    </button>
  </div>
</template>

<script setup lang="ts">
import { defineProps } from 'vue'

const props = defineProps<{
  type: string,
  buttonText: string,
  submitting: boolean
}>()
</script>
