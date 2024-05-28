package com.myblog7.controller;

import com.myblog7.payload.CommentDto;
import com.myblog7.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api/")
public class CommentController {
    private CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    //http://localhost:8080/api/posts/{postId}/comments

    //http://localhost:8080/api/posts/1/comments
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId,@RequestBody CommentDto commentDto){
        CommentDto Dto = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(Dto,HttpStatus.CREATED);
    }

    //http://localhost:8080/api/posts/5/comments
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable("postId") Long postId){
        List<CommentDto> commentsByPostId = commentService.getCommentsByPostId(postId);
        return commentsByPostId;
    }
    //http://localhost:8080/api/posts/5/comments/2
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public CommentDto getCommentById(@PathVariable("postId") Long postId,@PathVariable("commentId") Long CommentId){
        CommentDto commentById = commentService.getCommentById(postId, CommentId);
        return commentById;
    }
    //http://localhost:8080/api/comments
    @GetMapping("comments")
    List<CommentDto> getAllComments(){
        List<CommentDto> allComments = commentService.getAllComments();
        return allComments;
    }

    //http://localhost:8080/api/posts/5/comments/2
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteCommentById(@PathVariable("postId") Long postId,@PathVariable("commentId") Long commentId){
        commentService.deleteCommentById(postId,commentId);
        return new ResponseEntity<>("Comment is deleted...",HttpStatus.OK);
    }

}
