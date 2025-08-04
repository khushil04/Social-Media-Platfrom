package com.example.ConnectWithWorld.repository;

import com.example.ConnectWithWorld.entities.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<Story,Long> {

    @Query("Select s from Story s where s.user.id=:userId")
    List<Story> findAllStoryByUserId(@Param("userId")Long userId);

}
