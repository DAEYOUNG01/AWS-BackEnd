package com.bookbackend.backend.book.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookUpdateRequest {
    private String title;
    private String description;
    private String content;
    private String genre;
    private String author;
}
