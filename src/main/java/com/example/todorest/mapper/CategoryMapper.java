package com.example.todorest.mapper;

import com.example.todorest.dto.CategoryDto;
import com.example.todorest.dto.CreateCategoryRequestDto;
import com.example.todorest.entity.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category map(CreateCategoryRequestDto dto);

    CategoryDto mapToDto(Category entity);

    List<CategoryDto> mapListOfCategory(List<Category> list);

}
