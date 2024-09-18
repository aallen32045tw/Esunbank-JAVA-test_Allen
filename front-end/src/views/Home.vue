<template>
  <div class="home">
    <h1>歡迎來到圖書借閱系統</h1>
    <router-link to="/books" v-if="isLoggedIn">查看圖書列表</router-link>
    <router-link to="/login" v-else>登入</router-link>
  </div>
</template>

<script>
import { computed } from 'vue'
import { useStore } from 'vuex'

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Home',
  setup() {
    const store = useStore()
    const isLoggedIn = computed(() => store.getters.isLoggedIn)

    return { isLoggedIn }
  }
}
</script>