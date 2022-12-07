package com.sparta.postproject.service;

import com.sparta.postproject.dto.*;
import com.sparta.postproject.entity.Comment;
import com.sparta.postproject.entity.Member;
import com.sparta.postproject.entity.Post;
import com.sparta.postproject.entity.UserEnum;
import com.sparta.postproject.jwt.JwtUtil;
import com.sparta.postproject.repository.CommentRepository;
import com.sparta.postproject.repository.MemberRepository;
import com.sparta.postproject.repository.PostRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public CommentResponseDto createComment(Long id, HttpServletRequest request, CommentRequestDto requestDto) {
        String token = jwtUtil.resolveToken(request);
        Claims claims=null;

        if(token != null) {
            if(jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
            }
        }
        String username=claims.getSubject();

        Post found =  postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글을 찾을 수 없습니다.")
        );

        Member member = memberRepository.findByUsername(username).orElseThrow(
                () ->  new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );

        Comment comment = new Comment(requestDto, found, member);
        commentRepository.save(comment);
        return new CommentResponseDto(comment, username);
    }

    public CommentResponseDto updateComment(Long id, HttpServletRequest request, CommentRequestDto requestDto) {
        String token = jwtUtil.resolveToken(request);
        Claims claims=null;

        if(token != null) {
            if(jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
            }
        }
        String username=claims.getSubject();
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글을 찾을 수 없습니다.")
        );

        Member admin = memberRepository.findByUsertype(UserEnum.ADMIN).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );

        if (admin.getUsername().equals(username)) {
            comment.update(requestDto);
        } else if(comment.getMember().getUsername().equals(username)) {
            comment.update(requestDto);
        } else {
            throw new IllegalArgumentException("작성자와 로그인유저가 다른 댓글 입니다.");
        }
        return new CommentResponseDto(comment, username);
    }

    public ResponseDto deleteComment(Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims=null;

        if(token != null) {
            if(jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
            }
        }
        String username=claims.getSubject();
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글을 찾을 수 없습니다.")
        );
        Member admin = memberRepository.findByUsertype(UserEnum.ADMIN).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );

        if (admin.getUsername().equals(username)) {
            commentRepository.deleteById(id);
        } else if(comment.getMember().getUsername().equals(username)) {
            commentRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("작성자와 로그인유저가 다른 댓글 입니다.");
        }
        return new ResponseDto("댓글 삭제 성공", HttpStatus.OK.value());
    }
}
