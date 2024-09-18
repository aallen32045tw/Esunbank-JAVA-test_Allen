import { createRouter, createWebHistory } from 'vue-router'
import LoginForm from '../views/LoginForm.vue'
import RegistrationForm from '../views/RegistrationForm.vue'
import BookList from '../views/BookList.vue'
import Home from '../views/Home.vue'  
import store from '../store'  

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/login',
    name: 'Login',
    component: LoginForm
  },
  {
    path: '/register',
    name: 'Register',
    component: RegistrationForm
  },
  {
    path: '/books',
    name: 'BookList',
    component: BookList,
    meta: { requiresAuth: true }
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('../views/NotFound.vue')  // 404 頁面
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

// 路由守衛
router.beforeEach((to, from, next) => {
  const requiresAuth = to.matched.some(record => record.meta.requiresAuth)
  const isLoggedIn = store.getters.isLoggedIn  // 假設你的 store 中有這個 getter

  if (requiresAuth && !isLoggedIn) {
    next('/login')  // 如果需要認證但用戶未登入，重定向到登入頁面
  } else if ((to.name === 'Login' || to.name === 'Register') && isLoggedIn) {
    next('/')  // 如果用戶已登入，嘗試訪問登入或註冊頁面時重定向到首頁
  } else {
    next()  // 在所有其他情況下，允許導航
  }
})

export default router