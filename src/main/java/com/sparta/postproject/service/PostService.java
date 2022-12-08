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
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims=null;
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
            }
        }      //checkToekn 메소드를 통해 token 검증 진행

        String username=claims.getSubject();    //token값에서 username 추출하여 username 변수에 저장
        Member member = findMember(username);   //findMember 메소드를 통해 MemberRepository에서 username과 동일한 엔티티 검색

        Post post = new Post(requestDto, member);
        postRepository.save(post);

        return new PostResponseDto(post, username);
    }

    @Transactional(readOnly = true)
    public PostListResponseDto getPost() {
        PostListResponseDto postListResponseDto = new PostListResponseDto();
        List<Post> posts = postRepository.findAllByOrderByModifiedAtDesc();
        for (Post post : posts) {
            String username = post.getMember().getUsername();
            CommentListResponseDto commentListResponseDto = new CommentListResponseDto();
            List<Comment> comments = commentRepository.findByPost_Id(post.getId());
            for (Comment comment : comments) {
                commentListResponseDto.addComment(new CommentResponseDto(comment,comment.getMember().getUsername()));
            }
            postListResponseDto.addPost(new PostResponseDto(post, username, commentListResponseDto));
        }
        return postListResponseDto;
    }

    @Transactional
    public PostResponseDto getDetailPost(Long id) {
        Post post = checkPostId(id);    //전달받은 post id값의 게시글이 있는지 체크해서 저장/exception
        String username = post.getMember().getUsername();
        CommentListResponseDto commentListResponseDto = new CommentListResponseDto();
        List<Comment> comments = commentRepository.findByPost_Id(post.getId());
        for(Comment comment : comments) {
             commentListResponseDto.addComment(new CommentResponseDto(comment, comment.getMember().getUsername()));
        }
        return new PostResponseDto(post, username, commentListResponseDto);
    }

    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto, HttpServletRequest request) {
        Post post = checkPostId(id);    //전달받은 post id값의 게시글이 있는지 체크해서 저장/exception
        String token = jwtUtil.resolveToken(request);
        Claims claims = null;
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
            }
        }     //checkToekn 메소드를 통해 token 검증 진행

        String username = claims.getSubject();

        Member admin = findMember(username);    //findMember 메소드를 통해 MemberRepository에서 username과 동일한 엔티티 검색

        if (admin.getUsertype().equals(UserEnum.ADMIN)) {
            post.update(requestDto);
        } else if (post.getMember().getUsername().equals(username)) {
            post.update(requestDto);
        } else {
            throw new IllegalArgumentException("작성자와 로그인유저가 다른 게시글 입니다.");
        }
        return new PostResponseDto(post, username);
    }


    @Transactional
    public ResponseDto deletePost(Long id, HttpServletRequest request) {
        Post post = checkPostId(id);    //전달받은 post id값의 게시글이 있는지 체크해서 저장/exception

        //전달받은 토큰 검증
        String token = jwtUtil.resolveToken(request);
        Claims claims = null;
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
            }
        }

        String username = post.getMember().getUsername();
        Member admin = findMember(username);

        if (admin.getUsertype().equals(UserEnum.ADMIN)) {
            postRepository.deleteById(id);

        } else if (post.getMember().getUsername().equals(username)) {
            postRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("작성자와 로그인유저가 다른 게시글 입니다.");
        }
        return new ResponseDto("삭제가 완료되었습니다.", HttpStatus.OK.value());
    }


    //method
    public Post checkPostId(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        return post;
    }

    public Member findMember(String username) {
        Member admin = memberRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );
        return admin;
    }

}
