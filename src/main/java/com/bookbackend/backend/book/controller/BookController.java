package com.bookbackend.backend.book.controller;

import com.bookbackend.backend.book.dto.BookCreateRequest;
import com.bookbackend.backend.book.dto.BookResponse;
import com.bookbackend.backend.book.dto.BookUpdateRequest;
import com.bookbackend.backend.book.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Operation(summary = "책 등록", description = "특정 사용자가 새로운 책을 등록합니다.")
    @PostMapping
    public BookResponse createBook(
            @Parameter(description = "책을 등록하는 사용자 ID", example = "1")
            @RequestParam Long userId,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "등록할 책 정보",
                    required = true
            )
            @RequestBody BookCreateRequest request
    ){
        return bookService.createBook(userId, request);
    }

    @Operation(summary = "사용자별 책 목록 조회", description = "특정 사용자가 등록한 모든 책 리스트를 조회합니다.")
    @GetMapping()
    public List<BookResponse> getBooks(
            @Parameter(description = "조회할 사용자 ID", example = "1")
            @RequestParam Long userId
    ){
        return bookService.getBooksByUser(userId);
    }

    @Operation(summary = "책 상세 조회", description = "책 ID를 기반으로 책 상세 정보를 조회합니다.")
    @GetMapping("/{bookId}")
    public BookResponse getBook(
            @Parameter(description = "조회할 책 ID", example = "10")
            @PathVariable Long bookId
    ){
        return bookService.getBook(bookId);
    }

    @Operation(summary = "책 수정", description = "책 ID를 기반으로 책 정보를 수정합니다.")
    @PutMapping("/{bookId}")
    public BookResponse updateBook(
            @Parameter(description = "수정할 책 ID", example = "10")
            @PathVariable Long bookId,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "수정할 책 정보",
                    required = true
            )
            @RequestBody BookUpdateRequest request
    ){
        return bookService.updateBook(bookId, request);
    }

    @Operation(summary = "책 삭제", description = "책 ID를 기반으로 해당 책 데이터를 삭제합니다.")
    @DeleteMapping("/{bookId}")
    public void deleteBook(
            @Parameter(description = "삭제할 책 ID", example = "10")
            @PathVariable Long bookId
    ){
        bookService.deleteBook(bookId);
    }

    @GetMapping("/all")
    @Operation(
            summary = "전체 책 목록 조회",
            description = "등록된 모든 책 정보를 조회합니다."
    )
    public List<BookResponse> getAllBooks() {
        return bookService.getAllBooks();
    }

    // TODO: 작가로 책검색 추가 /books/getBookByAuthor

    // TODO: 책이름으로 검색 추가 /books/getBookByTitle
}