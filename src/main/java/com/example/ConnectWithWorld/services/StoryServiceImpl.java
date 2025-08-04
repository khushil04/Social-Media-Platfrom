package com.example.ConnectWithWorld.services;

import com.example.ConnectWithWorld.entities.Story;
import com.example.ConnectWithWorld.entities.User;
import com.example.ConnectWithWorld.exceptions.StoryException;
import com.example.ConnectWithWorld.exceptions.UserException;
import com.example.ConnectWithWorld.repository.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoryServiceImpl implements StoryService{


    private final UserService userService;
    private final StoryRepository storyRepository;

    @Override
    public Story createStory(Story story, Long userID) throws UserException{
        User user = userService.findUserById(userID);
        story.setUser(user);
        story.setTimestamp(LocalDateTime.now());
        user.getStories().add(story);
        return storyRepository.save(story);
    }
    @Override
    public List<Story> findStoryByUserId(Long userId) throws UserException, StoryException{
        User user = userService.findUserById(userId);
        List<Story> stories = storyRepository.findAllStoryByUserId(userId);
        return stories;
    }
}
