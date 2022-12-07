package com.sparta.postproject.dto;

import com.sparta.postproject.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PostResponseDto {
    private Long id;
    private String username;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private List<CommentResponseDto> commentList = new ArrayList<>();


    public PostResponseDto(Post post, String username) {
        this.id = post.getId();
        this.username = username;
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }

    public PostResponseDto(Post post, String username, CommentListResponseDto commentList) {
        this.id = post.getId();
        this.username = username;
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.commentList = commentList.getCommentList();
    }

}
