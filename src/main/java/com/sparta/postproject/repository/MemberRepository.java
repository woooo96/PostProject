package com.sparta.postproject.repository;

import com.sparta.postproject.entity.Member;
import com.sparta.postproject.entity.UserEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    //Member 엔티티에서 Username으로 엔티티 검색
    Optional<Member> findByUsername(String username);
    //Member 엔티티에서 Usertype으로 엔티티 검색
    Optional<Member> findByUsertype(UserEnum usertype);
}
