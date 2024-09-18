<template>
  <div id="app">
    <nav>
      <router-link to="/">首頁</router-link> |
      <router-link to="/books">圖書列表</router-link> |
      <router-link to="/register">註冊</router-link> |
      <router-link to="/login" v-if="!isLoggedIn">登入</router-link>
      <a href="#" @click.prevent="logout" v-else>登出</a>
      
    </nav>
    <router-view/>
  </div>
</template>

<script>
import { computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'App',
  setup() {
    const store = useStore()
    const router = useRouter()
    const isLoggedIn = computed(() => store.getters.isLoggedIn)

    const logout = () => {
      store.dispatch('logout')
      router.push('/login')
    }

    return { isLoggedIn, logout }
  }
}
</script>