package com.example.todorest.service;

import com.example.todorest.dto.CategoryDto;
import com.example.todorest.dto.CreateCategoryRequestDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {

    ResponseEntity<CategoryDto> create(CreateCategoryRequestDto createCategoryRequestDto);

    ResponseEntity<List<CategoryDto>> getAll();

    ResponseEntity<?> delete(int id);

}
