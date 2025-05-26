package com.study.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.study.entities.Course;

public interface ICourseRepo extends JpaRepository<Course, Long> {
    
}
