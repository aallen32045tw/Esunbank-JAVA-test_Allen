package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.repository.BorrowingRecordRepository;

@Service
public class BorrowingService {

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    @Transactional
    public void borrowBook(Long userId, Long inventoryId) throws Exception {
        try {
            borrowingRecordRepository.borrowBook(userId, inventoryId);
        } catch (Exception e) {
            throw new Exception("Unable to borrow book: " + e.getMessage());
        }
    }

    @Transactional
    public void returnBook(Long userId, Long inventoryId) throws Exception {
        try {
            borrowingRecordRepository.returnBook(userId, inventoryId);
        } catch (Exception e) {
            throw new Exception("Unable to return book: " + e.getMessage());
        }
    }
}