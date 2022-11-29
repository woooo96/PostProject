package com.sparta.postproject.controller;

import com.sparta.postproject.dto.*;
import com.sparta.postproject.entity.Post;
import com.sparta.postproject.repository.PostRepository;
import com.sparta.postproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

//    @GetMapping("/")
//    public ModelAndView home() { return new ModelAndView("main"); }

    @PostMapping("/api/post")
    public ResponseDto createPost(@RequestBody PostRequestDto requestDto) {
        return postService.createPost(requestDto);
    }

    @GetMapping("/api/post")
    public PostListResponseDto getPost() {
        return postService.getPost();
    }

    @GetMapping("/api/post/{id}")
    public PostResponseDto getDetailPost(@PathVariable long id) {
        return postService.getDetailPost(id);
    }

    @PutMapping("/api/post/{id}")
    public ResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        return postService.updatePost(id, requestDto);
    }

//    @ResponseBody
//    @PostMapping("api/post/{id}")
//    public Long DeletePost(@PathVariable Long id, @RequestParam String username, @RequestParam String password) {
//        return postService.deletePost(id, username, password);
//    }

    @DeleteMapping("/api/post/{id}")
    public ResponseDto DeletePost(@PathVariable Long id, @RequestBody PostDeleteRequestDto requestDto) {
        return postService.deletePost(id, requestDto);
    }



}
