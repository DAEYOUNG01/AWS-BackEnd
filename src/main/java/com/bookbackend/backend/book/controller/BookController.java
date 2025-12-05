package com.bookbackend.backend.book.controller;

import com.bookbackend.backend.book.dto.BookCreateRequest;
import com.bookbackend.backend.book.dto.BookResponse;
import com.bookbackend.backend.book.dto.BookUpdateRequest;
import com.bookbackend.backend.book.service.BookService;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    //책 등록
    @PostMapping
    public BookResponse createBook(@RequestParam Long userId,
                                   @RequestBody BookCreateRequest request){
        return bookService.createBook(userId, request);
    }
    //특정 유저의 책 목록 조회
    @GetMapping
    public List<BookResponse> getBooks(@RequestParam Long userId){
        return bookService.getBooksByUser(userId);
    }
    //책 상세 조회
    @GetMapping("/{bookId}")
    public BookResponse getBook(@PathVariable Long bookId){
        return bookService.getBook(bookId);
    }
    //책 수정
    @PutMapping("/{bookId}")
    public BookResponse updateBook(@PathVariable Long bookId,
                                   @RequestBody BookUpdateRequest request){
        return bookService.updateBook(bookId, request);
    }
    //책 삭제
    @DeleteMapping("/{bookId}")
    public void deleteBook(@PathVariable Long bookId){
        bookService.deleteBook(bookId);
    }
}
