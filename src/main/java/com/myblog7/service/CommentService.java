package com.myblog7.service;

import com.myblog7.payload.CommentDto;

import java.util.*;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(long postId);

    CommentDto getCommentById(Long postId, Long commentId);

    List<CommentDto> getAllComments();

    void deleteCommentById(Long postId, Long commentId);
}
