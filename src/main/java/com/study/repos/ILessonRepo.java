package com.study.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.study.entities.Lesson;

public interface ILessonRepo extends JpaRepository<Lesson, Long>{
    
}
