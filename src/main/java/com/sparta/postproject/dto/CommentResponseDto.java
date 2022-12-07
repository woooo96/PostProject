package com.sparta.postproject.dto;

import com.sparta.postproject.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String username;

    public CommentResponseDto(Comment comment, String username) {
        this.id=comment.getId();
        this.comment=comment.getComment();
        this.createdAt=comment.getCreatedAt();
        this.modifiedAt=comment.getModifiedAt();
        this.username=username;
    }
}
