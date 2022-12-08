package com.sparta.postproject.entity;

import com.sparta.postproject.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@NoArgsConstructor  //파라미터가 없는 기본 생성자생성해줌.
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(nullable = false)
//    private String username;
//
//    @Column(nullable = false)
//    private String password;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    //member엔티티와 ManyToOne으로 단방향 연관관계 형성
    @ManyToOne
    @JoinColumn(name="memberId", nullable = false)
    private Member member;

    //Comment 엔티티와 OneToMany 양방향 연관관계 형성
    @OneToMany(mappedBy="post", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();


    public Post(PostRequestDto requestDto, Member member) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.member = member;
    }

    public void update(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

}
