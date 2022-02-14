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
  <div v-show="problem" class="is-size-7 has-text-danger">{{ problem }}</div>
</template>

<script>
export default {
  name: 'TextField',
  props: {
    id: String,
    labelText: String,
    modelValue: String,
    problem: String
  }
}
</script>
