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

    //댓글 작성
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
        String username=claims.getSubject();    //username으로 claims.getSubject() 세팅

        //post entity중 입력받은 id 값의 게시글이 있는지 확인하여 found 참조변수에 입력
        Post found =  postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글을 찾을 수 없습니다.")
        );
        // member 참조변수에 토큰에서 추출한 username으로 사용자가 있는지 확인
        Member member = memberRepository.findByUsername(username).orElseThrow(
                () ->  new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );

        // comment 엔티티에 requestDto(title, comment) 정보와 postId외래키, memberId외래키 입력
        Comment comment = new Comment(requestDto, found, member);
        commentRepository.save(comment);    //comment 엔티티 저장
        return new CommentResponseDto(comment, username);
    }

    //댓글 수정
    public CommentResponseDto updateComment(Long id, HttpServletRequest request, CommentRequestDto requestDto) {
        //토큰 검증
        String token = jwtUtil.resolveToken(request);
        Claims claims=null;

        if(token != null) {
            if(jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
            }
        }
        String username=claims.getSubject();    //유저네임 세팅
        //id로 받은 comment id를 repository에서 검색해서 comment 참조변수에 엔티티 저장
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글을 찾을 수 없습니다.")
        );

        //UserEnum.ADMIN타입의 사용자 검색
        Member admin = memberRepository.findByUsertype(UserEnum.ADMIN).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );

        //UserEnum=ADMIN 인 사용자의 username과 토큰에서 추출한 username이 같을 경우 관리자이기 떄문에 update() 메소드 이용
        //comment.getMember().getUsername()을 통해 comment 작성자를 검색하고, 현재 토큰에 있는 사용자와 같을 경우 update, 아니면 익셉션 발생
        if (admin.getUsername().equals(username)) {
            comment.update(requestDto);
        } else if(comment.getMember().getUsername().equals(username)) {
            comment.update(requestDto);
        } else {
            throw new IllegalArgumentException("작성자와 로그인유저가 다른 댓글 입니다.");
        }
        return new CommentResponseDto(comment, username);
    }

    //댓글 삭제
    public ResponseDto deleteComment(Long id, HttpServletRequest request) {
        //토큰 검증
        String token = jwtUtil.resolveToken(request);
        Claims claims=null;

        if(token != null) {
            if(jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
            }
        }
        String username=claims.getSubject();    //username 세팅
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글을 찾을 수 없습니다.")
        );
        Member admin = memberRepository.findByUsertype(UserEnum.ADMIN).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );

        //UserEnum이 ADMIN인 사용자이거나 comment 작성자일경우 deleteById를 통해 댓글 삭제
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
