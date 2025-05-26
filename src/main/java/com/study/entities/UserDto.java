package com.study.entities;

import java.util.List;

import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String fullName;
    private String email;
    private String password;
    private List<String> roles;
    private String url;
    private String extra;
    private Long parentId;
}
