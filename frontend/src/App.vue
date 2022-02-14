<template>
  <NavBar/>

  <div class="container mt-6">
    <router-view/>
  </div>
</template>

<script lang="ts">
import NavBar from '@/components/NavBar.vue'

export default {
  components: { NavBar }
}
</script>

<style>
@import "https://cdn.jsdelivr.net/npm/bulma@0.9.3/css/bulma.min.css";
@import "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css";
</style>
