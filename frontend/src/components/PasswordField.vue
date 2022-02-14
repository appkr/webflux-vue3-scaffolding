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

<script>
import { defineComponent } from 'vue'

export default defineComponent({
  props: {
    id: String,
    labelText: String,
    modelValue: String,
    problem: Object
  }
})
</script>
