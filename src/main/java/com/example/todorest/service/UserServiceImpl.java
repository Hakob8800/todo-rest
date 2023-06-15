package com.example.todorest.service;

import com.example.todorest.dto.*;
import com.example.todorest.entity.User;
import com.example.todorest.entity.enums.Type;
import com.example.todorest.mapper.UserMapper;
import com.example.todorest.repository.UserRepository;
import com.example.todorest.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil tokenUtil;
    private final UserMapper userMapper;

    public ResponseEntity<UserAuthResponseDto> auth(UserAuthRequestDto userAuthRequestDto) {
        Optional<User> byEmail = userRepository.findByEmail(userAuthRequestDto.getEmail());
        if (byEmail.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = byEmail.get();
        if (!passwordEncoder.matches(userAuthRequestDto.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = tokenUtil.generateToken(userAuthRequestDto.getEmail());
        return ResponseEntity.ok(new UserAuthResponseDto(token));
    }

    public ResponseEntity<UserDto> register(CreateUserRequestDto createUserRequestDto){
        Optional<User> byEmail = userRepository.findByEmail(createUserRequestDto.getEmail());
        if(byEmail.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        User user = userMapper.map(createUserRequestDto);
        user.setPassword(passwordEncoder.encode(createUserRequestDto.getPassword()));
        user.setType(Type.USER);
        userRepository.save(user);
        return ResponseEntity.ok(userMapper.mapToDto(user));
    }

    @Override
    public ResponseEntity<GetUserResponseDto> getUser(int id) {
        return ResponseEntity.ok(userMapper.mapToGetResponseDto(userRepository.findById(id).get()));
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public ResponseEntity<GetUserResponseDto> updateUser(int id, CreateUserRequestDto createUserRequestDto) {
        Optional<User> fromDb = userRepository.findById(id);
        if (fromDb.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<User> byEmail = userRepository.findByEmail(createUserRequestDto.getEmail());
        if (byEmail.isPresent() && byEmail.get().getId() != id) {
            ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        User fromDB = fromDb.get();
        if (createUserRequestDto.getName() != null && !createUserRequestDto.getName().isEmpty()){
            fromDB.setName(createUserRequestDto.getName());
        }
        if (createUserRequestDto.getSurname() != null && !createUserRequestDto.getSurname().isEmpty()){
            fromDB.setSurname(createUserRequestDto.getSurname());
        }
        if (createUserRequestDto.getEmail() != null && !createUserRequestDto.getEmail().isEmpty()){
            fromDB.setEmail(createUserRequestDto.getEmail());
        }
        if (createUserRequestDto.getPassword() != null && !createUserRequestDto.getPassword().isEmpty()){
            fromDB.setPassword(passwordEncoder.encode(createUserRequestDto.getPassword()));
        }
        userRepository.save(fromDB);
        return ResponseEntity.ok(userMapper.mapToGetResponseDto(fromDB));
    }

}
