package com.myblog7.service.impl;

import com.myblog7.entity.Comment;
import com.myblog7.entity.Post;
import com.myblog7.exception.ResourceNotFound;
import com.myblog7.payload.CommentDto;
import com.myblog7.repository.CommentRepository;
import com.myblog7.repository.PostRepository;
import com.myblog7.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper mapper;
    public CommentServiceImpl(CommentRepository commentRepository,
                              PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }
    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Post not found with Id: "+postId)
        );
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);

        CommentDto dto=mapToDto(savedComment);
        return dto;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Post not found with Id: "+postId)
        );
        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentDto> commentDto = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());

        return commentDto;
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Post not found with Id: "+postId)
        );
        Comment comment=commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFound("Comment not found with Id: "+commentId)
        );
        CommentDto commentDto = mapToDto(comment);
        return commentDto;
    }

    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentDto> commentDto = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
        return commentDto;
    }

    @Override
    public void deleteCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Post not found with Id: "+postId)
        );
        Comment comment=commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFound("Comment not found with Id: "+commentId)
        );
        commentRepository.deleteById(commentId);
    }

    private CommentDto mapToDto(Comment savedComment) {
        CommentDto dto = mapper.map(savedComment, CommentDto.class);
        return dto;
    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = mapper.map(commentDto, Comment.class);
        return comment;
    }
}
