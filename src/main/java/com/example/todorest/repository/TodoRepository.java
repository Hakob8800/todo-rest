package com.example.todorest.repository;

import com.example.todorest.entity.Todo;
import com.example.todorest.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Integer> {

    Optional<List<Todo>> findByUserIdAndStatus(int userId, Status status);

    Optional<List<Todo>> findByUserIdAndCategoryId(int userId, int categoryId);

    Optional<List<Todo>> findByUserId(int userId);

}
