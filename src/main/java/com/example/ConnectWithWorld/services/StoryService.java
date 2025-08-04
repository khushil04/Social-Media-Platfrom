package com.example.ConnectWithWorld.services;

import com.example.ConnectWithWorld.entities.Story;
import com.example.ConnectWithWorld.entities.User;
import com.example.ConnectWithWorld.exceptions.StoryException;
import com.example.ConnectWithWorld.exceptions.UserException;

import java.util.List;

public interface StoryService {

    public Story createStory(Story story,Long userId) throws UserException;
    public List<Story> findStoryByUserId(Long userId) throws UserException, StoryException;
}
