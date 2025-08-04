package com.example.ConnectWithWorld.repository;


import com.example.ConnectWithWorld.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p where p.user.id=?1")
    public List<Post> findByUserId(Long userId);

    @Query("select p from Post p where p.user.id IN :users ORDER BY p.createdAt DESC")
    public List<Post> findAllPostByUserIds(@Param("users") List<Long> userId);
}
