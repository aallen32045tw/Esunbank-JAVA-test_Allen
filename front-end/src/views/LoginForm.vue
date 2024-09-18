<template>
  <div class="login-form">
    <h2>登入</h2>
    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <label for="phoneNumber">手機號碼：</label>
        <input 
          type="text" 
          id="phoneNumber" 
          v-model="phoneNumber" 
          required
          placeholder="請輸入手機號碼"
        >
      </div>
      <div class="form-group">
        <label for="password">密碼：</label>
        <input 
          type="password" 
          id="password" 
          v-model="password" 
          required
          placeholder="請輸入密碼"
        >
      </div>
      <button type="submit">登入</button>
    </form>
    <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
  </div>
</template>

<script>
import { ref } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'LoginForm',
  setup() {
    const store = useStore()
    const router = useRouter()
    const phoneNumber = ref('')
    const password = ref('')
    const errorMessage = ref('')

    const handleSubmit = async () => {
      try {
        await store.dispatch('login', {
          phoneNumber: phoneNumber.value,
          password: password.value
        })
        router.push('/') // 登入成功後導向首頁
      } catch (error) {
        errorMessage.value = '登入失敗，請檢查您的手機號碼和密碼'
        console.error('Login failed', error)
      }
    }

    return {
      phoneNumber,
      password,
      errorMessage,
      handleSubmit
    }
  }
}
</script>

<style scoped>
.login-form {
  max-width: 300px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
}
.form-group {
  margin-bottom: 15px;
}
label {
  display: block;
  margin-bottom: 5px;
}
input {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}
button {
  width: 100%;
  padding: 10px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
button:hover {
  background-color: #45a049;
}
.error-message {
  color: red;
  margin-top: 10px;
}
</style>