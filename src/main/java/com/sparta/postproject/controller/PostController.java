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

    @PostMapping("/post")
    public PostResponseDto createPost(@RequestBody PostRequestDto requestDto, HttpServletRequest request) {
        return postService.createPost(requestDto, request);
    }

    @GetMapping("/post")
    public PostListResponseDto getPost() {
        return postService.getPost();
    }

    @GetMapping("/post/{id}")
    public PostResponseDto getDetailPost(@PathVariable long id) {
        return postService.getDetailPost(id);
    }

    @PutMapping("/post/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, HttpServletRequest request) {

        return postService.updatePost(id, requestDto, request);
    }

    @DeleteMapping("/post/{id}")
    public ResponseDto DeletePost(@PathVariable Long id, HttpServletRequest request) {
        return postService.deletePost(id, request);
    }



}
