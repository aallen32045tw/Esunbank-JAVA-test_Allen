package com.example.demo.controller;

import com.example.demo.dto.BorrowingDto;
import com.example.demo.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/borrowing")
public class BorrowingController {

    @Autowired
    private BorrowingService borrowingService;

    @PostMapping("/borrow")
    public ResponseEntity<?> borrowBook(@RequestBody BorrowingDto borrowingDto) {
        try {
            borrowingService.borrowBook(borrowingDto.getUserId(), borrowingDto.getInventoryId());
            return ResponseEntity.ok("Book borrowed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnBook(@RequestBody BorrowingDto borrowingDto) {
        try {
            borrowingService.returnBook(borrowingDto.getUserId(), borrowingDto.getInventoryId());
            return ResponseEntity.ok("Book returned successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}