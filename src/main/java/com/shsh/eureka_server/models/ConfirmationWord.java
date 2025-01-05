package com.shsh.eureka_server.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "confirmation_words")
public class ConfirmationWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String word; // Кодовое слово
}