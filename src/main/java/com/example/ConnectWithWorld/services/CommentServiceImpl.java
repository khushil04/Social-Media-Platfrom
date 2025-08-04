package com.example.ConnectWithWorld.services;

import com.example.ConnectWithWorld.entities.Comment;
import com.example.ConnectWithWorld.entities.Post;
import com.example.ConnectWithWorld.entities.User;
import com.example.ConnectWithWorld.exceptions.CommentException;
import com.example.ConnectWithWorld.exceptions.PostException;
import com.example.ConnectWithWorld.exceptions.UserException;
import com.example.ConnectWithWorld.repository.CommentRepository;
import com.example.ConnectWithWorld.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserService userService;
    private final PostService postService;

    @Override
    public Comment createComment(Comment comment, Long userId, Long postId) throws UserException , PostException {
        User user = userService.findUserById(userId);
        Post post = postService.findPostById(postId);

        comment.setUser(user);
        comment.setCreatedAt(LocalDateTime.now());
        Comment createdComment = commentRepository.save(comment);
        post.getComments().add(createdComment);
        return createdComment;
    }


    public Comment findCommentById(Long commentId) throws CommentException {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException("Comment not found with id: " + commentId));
    }

    @Override
    public Comment likeComment(Long commentId, Long userId) throws UserException, CommentException {
        User user = userService.findUserById(userId);
        Comment comment = findCommentById(commentId);
        comment.getLikedByUsers().add(user);
        return commentRepository.save(comment);
    }

    @Override
    public Comment unlikeComment(Long commentId, Long userId) throws UserException, CommentException{
        User user = userService.findUserById(userId);
        Comment comment = findCommentById(commentId);
        comment.getLikedByUsers().remove(user);
        return commentRepository.save(comment);
    }
}
