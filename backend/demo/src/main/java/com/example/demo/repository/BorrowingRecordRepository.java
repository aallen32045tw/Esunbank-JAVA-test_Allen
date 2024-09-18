package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.BorrowingRecord;
import java.util.List;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {

    @Procedure(procedureName = "sp_borrow_book")
    void borrowBook(@Param("p_user_id") Long userId, 
                    @Param("p_inventory_id") Long inventoryId);

    @Procedure(procedureName = "sp_return_book")
    void returnBook(@Param("p_user_id") Long userId, 
                    @Param("p_inventory_id") Long inventoryId);

    @Query("SELECT br FROM BorrowingRecord br WHERE br.inventory.id = :inventoryId AND br.returnTime IS NULL")
    List<BorrowingRecord> findCurrentBorrower(@Param("inventoryId") Long inventoryId);

    
}
