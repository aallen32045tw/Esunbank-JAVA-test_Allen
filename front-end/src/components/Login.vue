<template>
  <div class="login">
    <h2>Login</h2>
    <form @submit.prevent="login">
      <input v-model="phoneNumber" type="text" placeholder="Phone Number" required>
      <input v-model="password" type="password" placeholder="Password" required>
      <button type="submit">Login</button>
    </form>
  </div>
</template>

<script>
import { ref } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  setup() {
    const store = useStore()
    const router = useRouter()
    const phoneNumber = ref('')
    const password = ref('')

    const login = async () => {
      try {
        await store.dispatch('login', {
          phoneNumber: phoneNumber.value,
          password: password.value
        })
        router.push('/')
      } catch (error) {
        console.error('Login failed', error)
      }
    }

    return { phoneNumber, password, login }
  }
}
</script>