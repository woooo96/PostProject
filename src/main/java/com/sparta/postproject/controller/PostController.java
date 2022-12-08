package com.sparta.postproject.controller;

import com.sparta.postproject.dto.*;
import com.sparta.postproject.entity.Post;
import com.sparta.postproject.repository.PostRepository;
import com.sparta.postproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    //게시글 작성하기
    @PostMapping("/post")
    public PostResponseDto createPost(@RequestBody PostRequestDto requestDto, HttpServletRequest request) {
        return postService.createPost(requestDto, request);
    }
    //게시글 전체 조회하기
    @GetMapping("/post")
    public PostListResponseDto getPost() {
        return postService.getPost();
    }
    //특정 게시글 상세 조회하기
    @GetMapping("/post/{id}")
    public PostResponseDto getDetailPost(@PathVariable long id) {
        return postService.getDetailPost(id);
    }

    //선택 게시글 수정하기
    @PutMapping("/post/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, HttpServletRequest request) {

        return postService.updatePost(id, requestDto, request);
    }

    //선택 게시글 삭제하기
    @DeleteMapping("/post/{id}")
    public ResponseDto DeletePost(@PathVariable Long id, HttpServletRequest request) {
        return postService.deletePost(id, request);
    }



}
