package com.example.demo.service;

import com.example.demo.entity.Book;
import com.example.demo.entity.BorrowingRecord;
import com.example.demo.entity.Inventory;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.BorrowingRecordRepository;
import com.example.demo.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    @Transactional(readOnly = true)
public List<Book> getAllBooks() {
    List<Book> books = bookRepository.findAll();
    
    for (Book book : books) {
        // 查詢與此書籍對應的所有庫存
        List<Inventory> inventoryList = inventoryRepository.findByIsbn(book.getIsbn());
        
        if (!inventoryList.isEmpty()) {
            Inventory inventory = inventoryList.get(0);  // 假設取第一個庫存
            book.setStatus(inventory.getStatus());
            book.setInventoryId(inventory.getId());  // 設置庫存 ID
            
            // 查詢借閱記錄，如果該書被借閱
            if ("borrowed".equals(inventory.getStatus())) {
                List<BorrowingRecord> borrowingRecords = borrowingRecordRepository.findCurrentBorrower(inventory.getId());
                
                // 確保只有一個未歸還的借閱記錄
                if (!borrowingRecords.isEmpty()) {
                    BorrowingRecord borrowingRecord = borrowingRecords.get(0); // 取第一個借閱記錄
                    book.setBorrowerId(borrowingRecord.getUser().getId());  // 設置當前借閱人
                }
            }
            
            // 設置書籍的庫存列表
            book.setInventoryList(inventoryList);
        } else {
            book.setStatus("No inventory available");
        }
    }
    return books;
}
    @Transactional
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }
}

