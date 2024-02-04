package com.example.firstproject.api;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentApiController {
    @Autowired
    private CommentService commentService;

    // GET
    @GetMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId){
        // 서비스에 위임
        List<CommentDto> dtos = commentService.comments(articleId);
        // 결과응
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    // POST
    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentDto> create(@PathVariable Long articleId,
                                             @RequestBody CommentDto dto){
        // 서비스에 위임
        CommentDto createdDto = commentService.create(articleId, dto);

        // 받은 결과 반환
        return ResponseEntity.status(HttpStatus.OK).body(createdDto);
    }

    // PATCH
    @PatchMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable Long id,
                                             @RequestBody CommentDto dto){

        // 서비스에 위임
        CommentDto updateDto = commentService.update(id, dto);

        // 받은 결과 반환
        return ResponseEntity.status(HttpStatus.OK).body(updateDto);
    }

    // DELETE
    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> delete(@PathVariable Long id){
        CommentDto deleteDto = commentService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(deleteDto);
    }

    }
