package com.example.todorest.endpoint;

import com.example.todorest.dto.*;
import com.example.todorest.security.CurrentUser;
import com.example.todorest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserEndpoint {

    private final UserService userService;

    @PostMapping("/auth")
    public ResponseEntity<UserAuthResponseDto> auth(@RequestBody UserAuthRequestDto userAuthRequestDto) {
        return userService.auth(userAuthRequestDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody CreateUserRequestDto createUserRequestDto) {
        return userService.register(createUserRequestDto);
    }

    @GetMapping
    public ResponseEntity<GetUserResponseDto> getUser(@AuthenticationPrincipal CurrentUser currentUser) {
        return userService.getUser(currentUser.getUser().getId());
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") int id) {
        userService.delete(id);
    }


    @PutMapping()
    public ResponseEntity<GetUserResponseDto> update(@RequestBody CreateUserRequestDto createUserRequestDto,
                                                     @AuthenticationPrincipal CurrentUser currentUser) {
        return userService.updateUser(currentUser.getUser().getId(), createUserRequestDto);
    }

}
