package com.sparta.postproject.repository;

import com.sparta.postproject.entity.Member;
import com.sparta.postproject.entity.UserEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);
    Optional<Member> findByUsertype(UserEnum usertype);
}
