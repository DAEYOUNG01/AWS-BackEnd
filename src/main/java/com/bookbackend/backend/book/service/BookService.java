package com.bookbackend.backend.book.service;

import com.bookbackend.backend.book.dto.*;
import com.bookbackend.backend.book.entity.Book;
import com.bookbackend.backend.book.repository.BookRepository;
import com.bookbackend.backend.global.exception.CustomException;
import com.bookbackend.backend.global.exception.ErrorCode;
import com.bookbackend.backend.user.entity.User;
import com.bookbackend.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    // 책 등록
    @Transactional
    public BookDetailResponse createBook(Long userId, BookCreateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Book book = Book.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .genre(request.getGenre())
                .author(request.getAuthor())
                .imageUrl(request.getImageUrl())
                .user(user)
                .build();

        Book saved = bookRepository.save(book);
        return BookDetailResponse.of(saved);
    }

    // 사용자별 책 목록 조회
    @Transactional(readOnly = true)
    public List<BookListResponse> getBooksByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        List<Book> books = bookRepository.findByUser(user);

        return books.stream()
                .map(BookListResponse::of)
                .toList();
    }

    // 책 상세 조회
    @Transactional(readOnly = true)
    public BookDetailResponse getBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));

        return BookDetailResponse.of(book);
    }

    // 책 수정
    @Transactional
    public BookDetailResponse updateBook(Long bookId, BookUpdateRequest request) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));

        book.setTitle(request.getTitle());
        book.setGenre(request.getGenre());
        book.setContent(request.getContent());
        book.setAuthor(request.getAuthor());
        book.setImageUrl(request.getImageUrl());

        Book updated = bookRepository.save(book);
        return BookDetailResponse.of(updated);
    }

    // 책 삭제
    @Transactional
    public void deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));

        bookRepository.delete(book);
    }
    // 책 검색
    @Transactional(readOnly = true)
    public List<BookListResponse> searchBooksByTitle(String title) {

        if (title == null || title.trim().isEmpty()){
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        }
        List<Book> books = bookRepository.findByTitleContaining(title);

        return books.stream()
                .map(BookListResponse::of)
                .toList();
    }
    //전체 책 조회
    @Transactional(readOnly = true)
    public List<BookListResponse> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookListResponse::of)
                .toList();
    }
    //책 이미지 추가
    @Transactional
    public BookDetailResponse updateBookImage(Long bookId, String  imageUrl) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));
        book.setImageUrl(imageUrl);

        Book updated = bookRepository.save(book);

        return BookDetailResponse.of(updated);
    }
}