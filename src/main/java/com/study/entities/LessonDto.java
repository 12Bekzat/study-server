package com.study.entities;

import lombok.Data;

@Data
public class LessonDto {
    private Long courseId;
    private String videoUrl;
    private String title;
    private String description;
    private boolean isReview;
    private Test test;
}
