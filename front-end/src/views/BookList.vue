<template>
  <div class="book-list">
    <h2>圖書列表</h2>
    <ul v-if="books.length">
      <li v-for="book in books" :key="book.inventoryId">
        {{ book.name }} by {{ book.author }}
        <p>Status: {{ book.status }}</p>
        <p>BorrowerId: {{ book.borrowerId }}</p> <!-- 用於檢查 borrowerId 是否正確 -->

        <!-- 顯示不同的按鈕依據書籍狀態與借閱人 -->
        <button @click="borrowBook(book.inventoryId)" v-if="book.status === 'available'">借書</button>
        <button @click="returnBook(book.inventoryId)" 
                v-if="book.status === 'borrowed' && book.borrowerId === store.state.user.id">還書</button>
        <span v-else-if="book.status === 'borrowed'">已借出</span>
      </li>
    </ul>
    <p v-else>暫無可用書籍</p>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { useStore } from 'vuex'

export default {
  name: 'BookList',
  setup() {
    const store = useStore()
    const books = ref([])

    // onMounted 生命週期鉤子，初始化獲取書籍列表
    onMounted(async () => {
      try {
        await store.dispatch('fetchBooks')
        books.value = store.state.books  // 確保 books 正確更新
      } catch (error) {
        console.error('Failed to fetch books:', error)
      }
    })

    // 借書按鈕邏輯
    const borrowBook = async (inventoryId) => {
      try {
        const userId = store.state.user.id  // 確保傳遞當前使用者的 userId
        await store.dispatch('borrowBook', { inventoryId, userId })  // 傳入 inventoryId 和 userId
        await store.dispatch('fetchBooks')  // 借書後刷新書籍列表
        books.value = store.state.books  // 確保 books 更新
      } catch (error) {
        console.error('Failed to borrow book:', error)
      }
    }

    // 還書按鈕邏輯
    const returnBook = async (inventoryId) => {
      try {
        const userId = store.state.user.id  // 確保 userId 正確傳遞
        await store.dispatch('returnBook', { inventoryId, userId })  // 傳入 inventoryId 和 userId
        await store.dispatch('fetchBooks')  // 還書後重新獲取書籍列表
        books.value = store.state.books  // 確保 books 正確更新
      } catch (error) {
        console.error('Failed to return book:', error)
      }
    }

    return { books, borrowBook, returnBook, store }
  }
}
</script>
