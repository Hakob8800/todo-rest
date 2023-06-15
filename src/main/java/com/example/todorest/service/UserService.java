package com.example.todorest.service;

import com.example.todorest.dto.*;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<UserAuthResponseDto> auth(UserAuthRequestDto userAuthRequestDto);

    ResponseEntity<UserDto> register(CreateUserRequestDto createUserRequestDto);

    ResponseEntity<GetUserResponseDto> getUser(int id);

    void delete(int id);

    ResponseEntity<GetUserResponseDto> updateUser(int id, CreateUserRequestDto createUserRequestDto);
}
