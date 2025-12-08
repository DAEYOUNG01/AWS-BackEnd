package com.bookbackend.backend.book.service;

import com.bookbackend.backend.book.dto.BookCreateRequest;
import com.bookbackend.backend.book.dto.BookDetailResponse;
import com.bookbackend.backend.book.dto.BookListResponse;
import com.bookbackend.backend.book.dto.BookUpdateRequest;
import com.bookbackend.backend.book.entity.Book;
import com.bookbackend.backend.book.repository.BookRepository;
import com.bookbackend.backend.user.entity.User;
import com.bookbackend.backend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    // 책 등록
    public BookDetailResponse createBook(Long userId, BookCreateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = Book.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .genre(request.getGenre())
                .author(request.getAuthor())
                .user(user)
                .build();

        Book saved = bookRepository.save(book);
        return BookDetailResponse.of(saved);
    }

    // 사용자별 책 목록 조회
    public List<BookListResponse> getBooksByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Book> books = bookRepository.findByUser(user);

        return books.stream()
                .map(BookListResponse::of)
                .toList();
    }

    // 책 상세 조회
    public BookDetailResponse getBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        return BookDetailResponse.of(book);
    }

    // 책 수정
    public BookDetailResponse updateBook(Long bookId, BookUpdateRequest request) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setTitle(request.getTitle());
        book.setGenre(request.getGenre());
        book.setContent(request.getContent());
        book.setAuthor(request.getAuthor());

        Book updated = bookRepository.save(book);
        return BookDetailResponse.of(updated);
    }

    // 책 삭제
    public void deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        bookRepository.delete(book);
    }
    // 책 검색
    public List<BookListResponse> searchBooksByTitle(String title) {
        List<Book> books = bookRepository.findByTitleContaining(title);

        return books.stream()
                .map(BookListResponse::of)
                .toList();
    }
}