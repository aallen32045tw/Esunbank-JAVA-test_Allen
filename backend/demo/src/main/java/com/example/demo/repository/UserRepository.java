package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 使用 procedureName 來正確指向資料庫中的儲存過程
    @Procedure(procedureName = "sp_register_user")
    void registerUser(@Param("p_phone_number") String phoneNumber, 
                      @Param("p_password") String password, 
                      @Param("p_user_name") String userName);

    // 如果儲存過程返回的是單行結果，可以使用 @Procedure，否則建議使用 @Query 或原生 SQL
    @Procedure(procedureName = "sp_login_user")
    User loginUser(@Param("p_phone_number") String phoneNumber);

    // 使用 JPA 自動生成的查詢方法
    User findByPhoneNumber(String phoneNumber);

}
