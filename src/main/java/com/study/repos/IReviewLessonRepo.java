package com.study.repos;

import com.study.entities.ReviewLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReviewLessonRepo extends JpaRepository<ReviewLesson, Long> {
}
