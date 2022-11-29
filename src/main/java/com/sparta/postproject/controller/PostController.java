package com.sparta.postproject.controller;

import com.sparta.postproject.dto.PostRequestDto;
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

    @GetMapping("/")
    public ModelAndView home() { return new ModelAndView("main"); }

    @PostMapping("/api/post")
    public Post createPost(@RequestBody PostRequestDto requestDto) { return postService.createPost(requestDto); }

    @GetMapping("/api/post")
    public List<Post> getPost() {
        return postService.getPost();
    }

    @GetMapping("/api/post/{id}")
    public Optional<Post> getDetailPost(@PathVariable long id, PostRequestDto requestDto) {
        return postService.getDetailPost(id, requestDto);
    }

    @PutMapping("/api/post/{id}")
    public Long updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        return postService.updatePost(id, requestDto);
    }

    @DeleteMapping("api/post/{id}")
    public Long DeletePost(@PathVariable Long id) {
        return postService.deletePost(id);
    }



}
