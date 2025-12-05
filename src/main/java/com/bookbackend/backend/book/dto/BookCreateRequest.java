package com.bookbackend.backend.book.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookCreateRequest {
    private String title;
    private String description;
    private String content;
    private String genre;
    private String author;
}
