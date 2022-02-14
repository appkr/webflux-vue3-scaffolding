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

<script>
import { defineComponent, computed } from 'vue'

export default defineComponent({
  props: {
    id: String,
    labelText: String,
    modelValue: String,
    problem: Object
  },
  setup(props) {
    const errorMessage = computed(() => {
      if (props.problem) {
        const fieldErrors = props.problem.violations.filter(violation => violation.field === 'name')
        if (fieldErrors.length > 0) {
          return fieldErrors[0].message
        }
      }

      return null
    })

    return { errorMessage }
  }
})
</script>
