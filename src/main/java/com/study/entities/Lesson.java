package com.study.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Lesson {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long Id;
    private Long courseId;
    private String videoUrl;
    private String title;
    @Lob
    private String description;
    private boolean isReview;
    
}
