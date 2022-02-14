<template>
  <div class="control has-text-centered mt-4">
    <button :type="type" class="button is-info" :disabled="submitting">
      {{ buttonText }}
    </button>
  </div>
</template>

<script>
import { defineComponent } from 'vue'

export default defineComponent({
  props: {
    type: String,
    buttonText: String,
    submitting: Boolean
  }
})
</script>
