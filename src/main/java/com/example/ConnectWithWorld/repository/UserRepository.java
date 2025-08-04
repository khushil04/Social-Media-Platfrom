package com.example.ConnectWithWorld.repository;

import com.example.ConnectWithWorld.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String userName);

    Optional<Object> findUserById(Long id);

    @Query("SELECT u FROM User u WHERE u.id IN :users")
    List<User> findAllUserByUserIds(@Param("users") List<Long> userIds);

    @Query("SELECT u FROM User u WHERE u.id IN :users")
    List<User> findAllUserIds(@Param("users") List<Long> userIds); // Fixed line

    @Query("SELECT DISTINCT u FROM User u WHERE u.username LIKE %:query% OR u.email LIKE %:query%")
    List<User> findByQuery(@Param("query") String query);
}

