<template>
  <div class="control has-text-centered mt-4">
    <button type="submit" class="button is-info" :disabled="submitting">
      Submit
    </button>
  </div>
</template>

<script>
export default {
  name: 'SubmitButton',
  props: {
    submitting: Boolean
  }
}
</script>

<style scoped>

</style>
