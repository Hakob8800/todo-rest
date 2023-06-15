package com.example.todorest.service;

import com.example.todorest.dto.CreateTodoRequestDto;
import com.example.todorest.dto.TodoResponseDto;
import com.example.todorest.entity.Todo;
import com.example.todorest.entity.enums.Status;
import com.example.todorest.mapper.TodoMapper;
import com.example.todorest.repository.CategoryRepository;
import com.example.todorest.repository.TodoRepository;
import com.example.todorest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;


    @Override
    public ResponseEntity<TodoResponseDto> create(int userId, CreateTodoRequestDto createTodoRequestDto) {
        Todo todo = todoMapper.map(createTodoRequestDto);
        todo.setUser(userRepository.findById(userId).get());
        todo.setStatus(Status.NOT_STARTED);
        todo.setCategory(categoryRepository.findById(createTodoRequestDto.getCategoryId()).get());
        todoRepository.save(todo);
        return ResponseEntity.ok(todoMapper.mapToDto(todo));
    }

    @Override
    public ResponseEntity<List<TodoResponseDto>> getByUserId(int userId) {
        Optional<List<Todo>> byUserId = todoRepository.findByUserId(userId);
        if (byUserId.isEmpty() || byUserId.get().size() == 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(todoMapper.mapToDtoList(byUserId.get()));
    }

    @Override
    public ResponseEntity<List<TodoResponseDto>> getByStatus(int useId, Status status) {
        Optional<List<Todo>> byUserIdAndStatus = todoRepository.findByUserIdAndStatus(useId, status);
        if (byUserIdAndStatus.isEmpty() || byUserIdAndStatus.get().size() == 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(todoMapper.mapToDtoList(byUserIdAndStatus.get()));
    }

    @Override
    public ResponseEntity<List<TodoResponseDto>> getByCategory(int userId, int categoryId) {
        Optional<List<Todo>> byUserIdAndCategoryId = todoRepository.findByUserIdAndCategoryId(userId, categoryId);
        if (byUserIdAndCategoryId.isEmpty() || byUserIdAndCategoryId.get().size() == 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(todoMapper.mapToDtoList(byUserIdAndCategoryId.get()));
    }

    @Override
    public ResponseEntity<TodoResponseDto> updateStatus(int userId, int todoId) {
        Optional<Todo> byId = todoRepository.findById(todoId);
        if (byId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Todo todo = byId.get();
        if (todo.getUser().getId() != userId) {
            return ResponseEntity.notFound().build();
        }
        if (todo.getStatus() == Status.DONE) {
            return ResponseEntity.ok(todoMapper.mapToDto(todo));
        }
        if (todo.getStatus() == Status.IN_PROCESS) {
            todo.setStatus(Status.DONE);
        }
        if (todo.getStatus() == Status.NOT_STARTED) {
            todo.setStatus(Status.IN_PROCESS);
        }
        todoRepository.save(todo);
        return ResponseEntity.ok(todoMapper.mapToDto(todo));
    }

    @Override
    public ResponseEntity<?> delete(int userId, int todoId) {
        Optional<Todo> byId = todoRepository.findById(todoId);
        if (byId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Todo todo = byId.get();
        if (todo.getUser().getId() != userId) {
            return ResponseEntity.notFound().build();
        }
        todoRepository.delete(todo);
        return ResponseEntity.noContent().build();

    }

}
