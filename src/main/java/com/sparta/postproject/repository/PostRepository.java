package com.sparta.postproject.repository;

import com.sparta.postproject.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    //Post엔티티 전부를 찾되 수정시간 내림차순으로 정렬해서 가져옴
    List<Post> findAllByOrderByModifiedAtDesc();



}
