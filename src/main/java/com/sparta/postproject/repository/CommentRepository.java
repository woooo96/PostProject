package com.sparta.postproject.repository;


import com.sparta.postproject.entity.Comment;
import com.sparta.postproject.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost_Id(Long postId);
}
