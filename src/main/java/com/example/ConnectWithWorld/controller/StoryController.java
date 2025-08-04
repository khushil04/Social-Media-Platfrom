package com.example.ConnectWithWorld.controller;

import com.example.ConnectWithWorld.entities.Story;
import com.example.ConnectWithWorld.entities.User;
import com.example.ConnectWithWorld.exceptions.StoryException;
import com.example.ConnectWithWorld.exceptions.UserException;
import com.example.ConnectWithWorld.services.StoryService;
import com.example.ConnectWithWorld.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class StoryController {
    private final StoryService storyService;

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Story>createStory(@RequestBody Story story, @RequestHeader("Authorization")String token) throws UserException, StoryException {
        User user = userService.findUserByToken(token);
        Story story1 = storyService.createStory(story,user.getId());
        return new ResponseEntity<Story>(story1, HttpStatus.OK);

    }
    @GetMapping("/{userId}")
    public ResponseEntity<List<Story>> findAllStoryUserId(@PathVariable Long userId)throws UserException,StoryException{
        List<Story > stories = storyService.findStoryByUserId(userId);
        return new ResponseEntity<>(stories,HttpStatus.OK);
    }

}
