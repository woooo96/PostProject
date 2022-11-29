package com.sparta.postproject.repository;

import com.sparta.postproject.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByModifiedAtDesc();
    Optional<Post> findByIdAndPassword(Long id, String password);

}
