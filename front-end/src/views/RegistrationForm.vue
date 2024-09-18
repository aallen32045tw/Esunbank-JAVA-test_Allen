<template>
  <div class="registration-form">
    <h2>註冊</h2>
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
      <div class="form-group">
        <label for="userName">用戶名稱：</label>
        <input 
          type="text" 
          id="userName" 
          v-model="userName" 
          required
          placeholder="請輸入用戶名稱"
        >
      </div>
      <button type="submit">註冊</button>
    </form>
    <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
    <p v-if="successMessage" class="success-message">{{ successMessage }}</p>
  </div>
</template>

<script>
import { ref } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'RegistrationForm',
  setup() {
    const store = useStore()
    const router = useRouter()
    const phoneNumber = ref('')
    const password = ref('')
    const userName = ref('')
    const errorMessage = ref('')
    const successMessage = ref('')

    const handleSubmit = async () => {
      try {
        await store.dispatch('register', {
          phoneNumber: phoneNumber.value,
          password: password.value,
          userName: userName.value
        })
        successMessage.value = '註冊成功！即將跳轉到登入頁面...'
        setTimeout(() => {
          router.push('/login')
        }, 2000)
      } catch (error) {
        errorMessage.value = '註冊失敗，請稍後再試'
        console.error('Registration failed', error)
      }
    }

    return {
      phoneNumber,
      password,
      userName,
      errorMessage,
      successMessage,
      handleSubmit
    }
  }
}
</script>

<style scoped>
.registration-form {
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
.success-message {
  color: green;
  margin-top: 10px;
}
</style>