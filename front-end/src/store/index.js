import { createStore } from 'vuex'
import axios from 'axios'

export default createStore({
  state: {
    user: null,     // 保存用戶信息
    authToken: null, // 保存 JWT token
    books: []       // 保存書籍列表
  },
  mutations: {
    setUser(state, user) {
      state.user = user
    },
    setAuthToken(state, token) {
      state.authToken = token
    },
    clearAuthToken(state) {
      state.authToken = null
    },
    setBooks(state, books) {
      state.books = books
    }
  },
  actions: {
    async login({ commit }, credentials) {
      try {
        const response = await axios.post('http://localhost:8080/api/users/login', credentials)
        const token = response.data.token
        const userId = response.data.userId  // 從響應中獲取 userId
    
        // 保存 token 和 userId
        commit('setUser', { id: userId })
        commit('setAuthToken', token)
    
        // 把 token 保存到 localStorage 以保持登錄狀態
        localStorage.setItem('authToken', token)
        localStorage.setItem('userId', userId)  // 可選：把 userId 也存儲在 localStorage 中
    
        return response
      } catch (error) {
        console.error('Login failed', error)
        throw error
      }
    },
    async register(_, userData) {
      try {
        const response = await axios.post('http://localhost:8080/api/users/register', userData)
        return response
      } catch (error) {
        console.error('Registration failed', error)
        throw error
      }
    },
    async fetchBooks({ commit, state }) {
      try {
        const token = state.authToken
        
        // 使用 Bearer token 發送請求
        const response = await axios.get('http://localhost:8080/api/books', {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        })
        commit('setBooks', response.data)
      } catch (error) {
        console.error('Error fetching books:', error)
        throw error
      }
    },
    async borrowBook({ dispatch, state }, { inventoryId }) {  // 傳入 inventoryId
      try {
        const token = state.authToken
  
        // 發送借書請求時附帶 token 和 inventoryId
        await axios.post('http://localhost:8080/api/borrowing/borrow', { 
          userId: state.user.id,   // 從 state 取得 userId
          inventoryId: inventoryId  // 傳入 inventoryId
        }, {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        })
        
        // 借書成功後重新獲取書籍列表
        dispatch('fetchBooks')
      } catch (error) {
        console.error('Error borrowing book:', error)
        throw error
      }
    },
    async returnBook({ state }, { inventoryId, userId }) {
      try {
        const token = state.authToken
        await axios.post('http://localhost:8080/api/borrowing/return', 
          { 
            inventoryId, 
            userId 
          }, 
          {
            headers: {
              'Authorization': `Bearer ${token}`
            }
          }
        )
      } catch (error) {
        console.error('Error returning book:', error)
        throw error
      }
    },
    logout({ commit }) {
      // 清除 Vuex 中的 token 和用戶信息
      commit('clearAuthToken')
      commit('setUser', null)

      // 清除 localStorage 中的 token
      localStorage.removeItem('authToken')
    },
    checkAuthToken({ commit }) {
      // 在應用初始化時從 localStorage 中檢查 token
      const token = localStorage.getItem('authToken')
      if (token) {
        commit('setAuthToken', token)
      }
    }
  },
  getters: {
    isLoggedIn: state => !!state.authToken,  // 判斷用戶是否已登錄
    authToken: state => state.authToken
  }
})
