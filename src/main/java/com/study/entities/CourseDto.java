package com.study.entities;

import java.util.List;

import lombok.Data;

@Data
public class CourseDto {
    private String title;
    private String description;
    private int preview;
    private List<LessonDto> lessons;
}
