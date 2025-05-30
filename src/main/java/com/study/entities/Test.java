package com.study.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Test {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long Id;
    private String title;
    @Lob
    private String questions;
    private Long lessonId;
}
