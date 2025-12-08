package com.bookbackend.backend.book.controller;

import com.bookbackend.backend.book.dto.BookCreateRequest;
import com.bookbackend.backend.book.dto.BookDetailResponse;
import com.bookbackend.backend.book.dto.BookListResponse;
import com.bookbackend.backend.book.dto.BookUpdateRequest;
import com.bookbackend.backend.book.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    // 책 등록
    @Operation(summary = "책 등록", description = "특정 사용자가 새로운 책을 등록합니다.")
    @PostMapping
    public BookDetailResponse createBook(
            @Parameter(description = "책을 등록하는 사용자 ID", example = "1")
            @RequestParam Long userId,
            @Valid @RequestBody BookCreateRequest request
    ) {
        return bookService.createBook(userId, request);
    }

    // 사용자별 책 목록 조회
    @Operation(summary = "사용자별 책 목록", description = "특정 사용자가 등록한 책 목록을 조회합니다.")
    @GetMapping
    public List<BookListResponse> getBooks(
            @Parameter(description = "조회할 사용자 ID", example = "1")
            @RequestParam Long userId
    ) {
        return bookService.getBooksByUser(userId);
    }

    // 책 상세 조회
    @Operation(summary = "책 상세 조회", description = "책 ID로 상세 정보를 조회합니다.")
    @GetMapping("/{bookId}")
    public BookDetailResponse getBook(
            @Parameter(description = "조회할 책 ID", example = "10")
            @PathVariable Long bookId
    ) {
        return bookService.getBook(bookId);
    }

    // 책 수정
    @Operation(summary = "책 수정", description = "책 ID로 책 정보를 수정합니다.")
    @PutMapping("/{bookId}")
    public BookDetailResponse updateBook(
            @Parameter(description = "수정할 책 ID", example = "10")
            @PathVariable Long bookId,
            @Valid @RequestBody BookUpdateRequest request
    ) {
        return bookService.updateBook(bookId, request);
    }

    // 책 삭제
    @Operation(summary = "책 삭제", description = "책 ID로 책을 삭제합니다.")
    @DeleteMapping("/{bookId}")
    public void deleteBook(
            @Parameter(description = "삭제할 책 ID", example = "10")
            @PathVariable Long bookId
    ) {
        bookService.deleteBook(bookId);
    }
    //책 검색
    @Operation(summary = "책 검색", description = "책 title로 책 검색")
    @GetMapping("/search")
    public List<BookListResponse> searchBooks(@RequestParam String title){
        return bookService.searchBooksByTitle(title);
    }
}