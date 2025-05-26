package com.study.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.study.entities.Test;

public interface ITestRepo extends JpaRepository<Test, Long> {
    
}
