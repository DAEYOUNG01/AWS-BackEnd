package com.bookbackend.backend.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
@Tag(name = "Book API", description = "도서 관련 API")
public class tempController {

    @GetMapping("/{id}")
    @Operation(summary = "도서 상세 조회", description = "ID로 특정 책의 상세 정보를 조회합니다.")
    public String getBook(@PathVariable Long id) {
        return "dummy";
    }
}
