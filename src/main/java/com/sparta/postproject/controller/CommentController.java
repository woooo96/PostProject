package com.sparta.postproject.controller;

import com.sparta.postproject.dto.CommentRequestDto;
import com.sparta.postproject.dto.CommentResponseDto;
import com.sparta.postproject.dto.PostResponseDto;
import com.sparta.postproject.dto.ResponseDto;
import com.sparta.postproject.entity.Comment;
import com.sparta.postproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class CommentController {

    private final CommentService commentService;

    //코맨트 작성하기
    @PostMapping("/{id}/comment")
    public CommentResponseDto createComment(@PathVariable Long id, HttpServletRequest request, @RequestBody CommentRequestDto requestDto) {
        return commentService.createComment(id, request, requestDto);
    }

    //코맨트 수정하기
    @PutMapping("/comment/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id, HttpServletRequest request, @RequestBody CommentRequestDto requestDto) {
        return commentService.updateComment(id, request, requestDto);
    }

    //코맨트 삭제하기
    @DeleteMapping("/comment/{id}")
    public ResponseDto deleteComment(@PathVariable Long id, HttpServletRequest request) {
        return commentService.deleteComment(id, request);
    }
}
