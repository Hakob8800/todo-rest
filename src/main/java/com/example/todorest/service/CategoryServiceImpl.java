package com.example.todorest.service;

import com.example.todorest.dto.CategoryDto;
import com.example.todorest.dto.CreateCategoryRequestDto;
import com.example.todorest.entity.Category;
import com.example.todorest.mapper.CategoryMapper;
import com.example.todorest.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public ResponseEntity<CategoryDto> create(CreateCategoryRequestDto createCategoryRequestDto) {
        Optional<Category> byName = categoryRepository.findByName(createCategoryRequestDto.getName());
        if(byName.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Category category = categoryMapper.map(createCategoryRequestDto);
        categoryRepository.save(category);
        return ResponseEntity.ok(categoryMapper.mapToDto(category));
    }

    @Override
    public ResponseEntity<List<CategoryDto>> getAll() {
        List<Category> all = categoryRepository.findAll();
        if (all.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<CategoryDto> allDto= categoryMapper.mapListOfCategory(all);
        return ResponseEntity.ok(allDto);
    }

    @Override
    public ResponseEntity<?> delete(int id) {
        if(categoryRepository.existsById(id)){
            categoryRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
