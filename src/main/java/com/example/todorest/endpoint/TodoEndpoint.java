package com.example.todorest.endpoint;

import com.example.todorest.dto.CreateTodoRequestDto;
import com.example.todorest.dto.TodoResponseDto;
import com.example.todorest.entity.enums.Status;
import com.example.todorest.security.CurrentUser;
import com.example.todorest.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.DeclareError;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todos")
public class TodoEndpoint {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoResponseDto> create(@AuthenticationPrincipal CurrentUser currentUser,
                                                  @RequestBody CreateTodoRequestDto createTodoRequestDto) {
        return todoService.create(currentUser.getUser().getId(), createTodoRequestDto);
    }

    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> getAll(@AuthenticationPrincipal CurrentUser currentUser) {
        return todoService.getByUserId(currentUser.getUser().getId());
    }

    @GetMapping("/byCategory")
    public ResponseEntity<List<TodoResponseDto>> getByCategory(@AuthenticationPrincipal CurrentUser currentUser,
                                                               @RequestParam("categoryId") int categoryId) {
        return todoService.getByCategory(currentUser.getUser().getId(), categoryId);
    }

    @GetMapping("/byStatus")
    public ResponseEntity<List<TodoResponseDto>> getByStatus(@AuthenticationPrincipal CurrentUser currentUser,
                                                               @RequestParam("status") Status status) {
        return todoService.getByStatus(currentUser.getUser().getId(), status);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponseDto> update(@AuthenticationPrincipal CurrentUser currentUser,
                                                  @PathVariable int id){
        return todoService.updateStatus(currentUser.getUser().getId(),id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal CurrentUser currentUser,
                                                  @PathVariable int id){
        return todoService.delete(currentUser.getUser().getId(),id);
    }

}
