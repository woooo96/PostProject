package com.sparta.postproject.service;

import com.sparta.postproject.dto.*;
import com.sparta.postproject.entity.Post;
import com.sparta.postproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public ResponseDto createPost(PostRequestDto requestDto) {
        Post post = new Post(requestDto);
        postRepository.save(post);
        return new ResponseDto("정상등록 되었습니다.", HttpStatus.OK.value());
    }

    @Transactional(readOnly = true)
    public PostListResponseDto getPost() {
        PostListResponseDto postListResponseDto = new PostListResponseDto();
        List<Post> posts = postRepository.findAllByOrderByModifiedAtDesc();
        for (Post post : posts) {
            postListResponseDto.addPost(new PostResponseDto(post));
        }
        return postListResponseDto;
    }

    @Transactional
    public PostResponseDto getDetailPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        return new PostResponseDto(post);
    }
    @Transactional
    public ResponseDto updatePost(Long id, PostRequestDto requestDto) {
        Post post = postRepository.findByIdAndPassword(id, requestDto.getPassword()).orElseThrow(
                () -> new IllegalArgumentException("패스워드가 틀렸습니다.")
        );
    post.update(requestDto);
    return new ResponseDto("수정이 완료되었습니다.", HttpStatus.OK.value());
    }

//    @Transactional
//    public Long deletePost(Long id, String username, String password) {
//        postRepository.findByIdAndUsernameAndPassword(id, username, password).orElseThrow(
//                () -> new IllegalArgumentException("아이디/패스워드가 틀렸습니다.")
//        );
//        postRepository.deleteById(id);
//        return id;
//    }

    @Transactional
    public ResponseDto deletePost(Long id, PostDeleteRequestDto requestDto) {
        postRepository.findByIdAndPassword(id, requestDto.getPassword()).orElseThrow(
                () -> new IllegalArgumentException("패스워드가 틀렸습니다.")
        );
        postRepository.deleteById(id);
        return new ResponseDto("삭제가 완료되었습니다.", HttpStatus.OK.value());
    }
}
