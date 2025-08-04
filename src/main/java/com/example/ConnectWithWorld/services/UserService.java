package com.example.ConnectWithWorld.services;

import com.example.ConnectWithWorld.entities.User;
import com.example.ConnectWithWorld.exceptions.UserException;

import java.util.List;

public interface UserService {



    public User findUserById(Long id) throws UserException;

    public User findUserByToken(String token) throws UserException;

    public User findUserByUsername(String Username) throws UserException;

    public String followUser(Long reqUserId, Long followUserId) throws UserException;

    public String unfollowUser(Long reqUserId, Long followUserId) throws UserException;

    public List<User> findUserByIDs(List<Long> userIds) throws UserException;

    public List<User> searchUser(String query)throws UserException;
    public User updateUser(User updatedUser, User existingUser) throws UserException;



}
