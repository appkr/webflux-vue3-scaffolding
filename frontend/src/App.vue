<template>
  <nav class="navbar has-shadow is-white">
    <div class="navbar-brand">
      <a class="navbar-item">
        Logo
      </a>
      <a class="navbar-burger" @click="toggleMenu">
        <span></span>
        <span></span>
        <span></span>
      </a>
    </div>

    <div class="navbar-menu" :class="{ 'is-active' : showMenu }">
      <div class="navbar-start">
        <router-link to="/" tag="a" class="navbar-item">Home</router-link>
        <router-link to="/about" tag="a" class="navbar-item">About</router-link>
      </div>
    </div>
  </nav>
  <div class="container mt-6">
    <router-view/>
  </div>
</template>

<script lang="ts">
import { ref } from 'vue'

export default {
  setup (): Record<string, unknown> {
    const showMenu = ref(false)

    const toggleMenu = () => {
      showMenu.value = !showMenu.value
    }

    return {
      showMenu,
      toggleMenu
    }
  }
}
</script>

<style>
@import "https://cdn.jsdelivr.net/npm/bulma@0.9.3/css/bulma.min.css";
</style>
