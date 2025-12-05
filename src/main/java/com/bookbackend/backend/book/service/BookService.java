package com.bookbackend.backend.book.service;

import com.bookbackend.backend.book.dto.BookCreateRequest;
import com.bookbackend.backend.book.dto.BookResponse;
import com.bookbackend.backend.book.dto.BookUpdateRequest;
import com.bookbackend.backend.book.entity.Book;
import com.bookbackend.backend.book.entity.User;
import com.bookbackend.backend.book.repository.BookRepository;
import com.bookbackend.backend.book.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    //책 등록
    public BookResponse createBook(Long userId, BookCreateRequest request){
        // userId로 User 엔티티 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // Book 엔티티 생성
        Book book = Book.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .content(request.getContent())
                .genre(request.getGenre())
                .author(request.getAuthor())
                .user(user)
                .build();
        //DB에 저장
        Book saved = bookRepository.save(book);

        // 저장된 엔티티를 BookResponse로 변환 후 반환
        return BookResponse.of(saved);
    }
    //사용자별 책 목록 조회
    public List<BookResponse> getBooksByUser(Long userId){
       User user = userRepository.findById(userId)
               .orElseThrow(() -> new RuntimeException("User not found"));
       List<Book> books = bookRepository.findByUser(user);

       return books.stream()
               .map(BookResponse::of)
               .toList();
    }
    //책 상세 내용 조회
    public BookResponse getBook(Long bookId){
       Book book = bookRepository.findById(bookId)
               .orElseThrow(() -> new RuntimeException("Book not found"));

       return BookResponse.of(book);
    }
    //책 수정
    public BookResponse updateBook(Long bookId, BookUpdateRequest request){
       Book book = bookRepository.findById(bookId)
               .orElseThrow(() -> new RuntimeException("Book not found"));

       book.setTitle(request.getTitle());
       book.setDescription(request.getDescription());
       book.setGenre(request.getGenre());
       book.setContent(request.getContent());
       book.setAuthor(request.getAuthor());

       Book updated = bookRepository.save(book);

       return BookResponse.of(updated);
    }
    //책 삭제
    public void deleteBook(Long bookID){

        Book book = bookRepository.findById(bookID)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        bookRepository.delete(book);
    }
}

