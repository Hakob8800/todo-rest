package com.example.todorest.mapper;

import com.example.todorest.dto.CreateTodoRequestDto;
import com.example.todorest.dto.TodoResponseDto;
import com.example.todorest.entity.Todo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface TodoMapper {

    Todo map(CreateTodoRequestDto dto);

    TodoResponseDto mapToDto(Todo entity);

    List<TodoResponseDto> mapToDtoList(List<Todo> todoList);

}
