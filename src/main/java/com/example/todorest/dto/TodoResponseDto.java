package com.example.todorest.dto;

import com.example.todorest.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoResponseDto {

    private UserDto user;
    private String title;
    private CategoryDto category;
    private Status status;
}
