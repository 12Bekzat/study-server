package com.study.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int count;
    private Long userId;
}
