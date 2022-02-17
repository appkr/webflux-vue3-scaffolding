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

    <div class="navbar-end">
      <div class="navbar-item">
        <div class="buttons" v-if="!isLoggedIn">
          <router-link to="/login" class="button is-light">
            Login
          </router-link>
        </div>
        <div v-else>
          <p>
            <i class="fa-solid fa-user"></i>
            {{ username }}
          </p>
        </div>
      </div>
    </div>
  </nav>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue'
import { useStore } from 'vuex'

const showMenu = ref(false)

const toggleMenu = () => {
  showMenu.value = !showMenu.value
}

const store = useStore()
const username = computed(() => {
  return store.state.userState?.username ?? 'unknown'
})

const isLoggedIn = computed(() => {
  return store.state.userState?.isLoggedIn ?? false
})
</script>
