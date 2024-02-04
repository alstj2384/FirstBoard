package com.example.firstproject.service;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;

    // 1. 댓글 조회
    public List<CommentDto> comments(Long articleId){
        return commentRepository.findByArticleId(articleId)
                .stream()
                .map(comment -> CommentDto.createCommentDto(comment))
                .collect(Collectors.toList());
    }

    // 2. 댓글 생성
    @Transactional
    public CommentDto create(Long articleId, CommentDto dto) {
        // 1. DB에서 부모 게시글을 조회해 가져오고 게시글이 없을 경우 예외 발생시키기
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! " +
                        "대상 게시글이 없습니다."));

        // 2. 부모 게시글의 새 댓글 엔티티 생성하기
        Comment comment = Comment.createComment(dto, article);

        // 3. 생성된 엔티티를 DB에 저장하기
        Comment created = commentRepository.save(comment);

        // 4. DB에 저장한 엔티티를 DTO로 변환해 반환하기
        return CommentDto.createCommentDto(created);
    }

    @Transactional
    public CommentDto update(Long commentId, CommentDto dto) {
        // 1. id로 댓글 조회해 가져오고 해당 댓글이 없을 경우 예외 발생시키기
        Comment target = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패!" +
                        "대상 댓글이 없습니다"));
        // 2. dto로 업데이트 할 Comment 객체 생성하기
        target.patch(dto);
        // 3. DB에 저장하기
        Comment updated = commentRepository.save(target);
        // 4. 저장한 엔티티 반환하기
        return CommentDto.createCommentDto(updated);
    }

    @Transactional
    public CommentDto delete(Long id) {
        // 1. 댓글 조회하기 없으면 예외 출력
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! " +
                        "대상 댓글이 없습니다"));
        // 2. 엔티티 삭제하기
        commentRepository.delete(target);
        // 3. 삭제한 객체를 dto로 반환하기
        return CommentDto.createCommentDto(target);
    }
}
