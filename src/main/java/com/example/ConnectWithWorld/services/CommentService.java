package com.example.ConnectWithWorld.services;

import com.example.ConnectWithWorld.entities.Comment;
import com.example.ConnectWithWorld.exceptions.CommentException;
import com.example.ConnectWithWorld.exceptions.PostException;
import com.example.ConnectWithWorld.exceptions.UserException;

public interface CommentService {

    public Comment createComment(Comment comment, Long userId, Long postId) throws UserException, PostException;


    public Comment findCommentById(Long commentId) throws CommentException;

    public Comment likeComment(Long commentId, Long userId) throws UserException, CommentException;

    public Comment unlikeComment(Long commentId, Long userId) throws UserException, CommentException;


}
