package com.example.todorest.entity;

import com.example.todorest.entity.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    @ManyToOne
    private User user;
    @ManyToOne
    private Category category;
    @Enumerated(EnumType.STRING)
    private Status status;
}
