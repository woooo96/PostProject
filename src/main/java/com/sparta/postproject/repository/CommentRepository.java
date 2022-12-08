package com.sparta.postproject.repository;


import com.sparta.postproject.entity.Comment;
import com.sparta.postproject.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    //Comment 엔티티에서 외래키로 설정된 포스트ID로 검색한 결과 추출
    List<Comment> findByPost_Id(Long postId);
}
