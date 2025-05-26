package com.study.entities;

import lombok.Data;

@Data
public class UpdateUserDto {
    private Long id;
    private UserDto userDto;
}
