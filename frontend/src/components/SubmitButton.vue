<template>
  <div class="control has-text-centered mt-4">
    <button type="submit" class="button is-info" :disabled="submitting">
      Submit
    </button>
  </div>
</template>

<script>
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'SubmitButton',
  props: {
    submitting: Boolean
  }
})
</script>
