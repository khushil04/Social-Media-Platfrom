package com.example.ConnectWithWorld.services;

import com.example.ConnectWithWorld.entities.Post;
import com.example.ConnectWithWorld.exceptions.UserException;
import com.example.ConnectWithWorld.exceptions.PostException;

import java.util.List;

public interface PostService {

    Post createPost(Post post, Long userId) throws UserException;

    String deletePost(Long postId, Long userId) throws UserException, PostException;

    List<Post> findPostByUserId(Long userId) throws UserException;

    Post findPostById(Long postId) throws PostException;

    List<Post> findAllPostByUserIds(List<Long> userIds) throws PostException, UserException;

    String savePost(Long postId, Long userId) throws UserException, PostException;

    String unsavedPost(Long postId, Long userId) throws UserException, PostException;

    Post likePost(Long postId, Long userId) throws UserException, PostException;

    Post unLikePost(Long postId, Long userId) throws UserException, PostException;
}
