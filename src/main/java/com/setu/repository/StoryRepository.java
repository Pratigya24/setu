package com.setu.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.setu.entity.Story;

public interface StoryRepository extends JpaRepository<Story, Long> {
    List<Story> findTop6ByOrderByCreatedAtDesc();
    List<Story> findAllByOrderByCreatedAtDesc();
}