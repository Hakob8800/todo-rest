package com.example.todorest.service;

import com.example.todorest.dto.CreateTodoRequestDto;
import com.example.todorest.dto.TodoResponseDto;
import com.example.todorest.entity.enums.Status;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TodoService {

    ResponseEntity<TodoResponseDto> create(int userId, CreateTodoRequestDto createTodoRequestDto);

    ResponseEntity<List<TodoResponseDto>> getByUserId(int userId);

    ResponseEntity<List<TodoResponseDto>> getByStatus(int userId, Status status);

    ResponseEntity<List<TodoResponseDto>> getByCategory(int userId, int categoryId);

    ResponseEntity<TodoResponseDto> updateStatus(int userId, int todoId);

    ResponseEntity<?> delete(int userId, int todoId);

}
