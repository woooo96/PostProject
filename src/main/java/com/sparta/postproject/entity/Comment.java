package com.sparta.postproject.entity;

import com.sparta.postproject.dto.CommentRequestDto;
import com.sparta.postproject.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comment;

    //post 엔티티 ManyToOne으로 양방향 연관관계 형성
    @ManyToOne
    private Post post;

    //member 엔티티 ManyToOne으로 단방향 연관관계 형성
    @ManyToOne
    @JoinColumn(name="memberId", nullable = false)
    private Member member;


    public Comment(CommentRequestDto commentRequestDto, Post post, Member member) {
        this.comment=commentRequestDto.getComment();
        this.post=post;
        this.member=member;
    }

    public void update(CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();
    }




}
