package com.example.ConnectWithWorld.controller;


import com.example.ConnectWithWorld.entities.User;
import com.example.ConnectWithWorld.exceptions.UserException;
import com.example.ConnectWithWorld.response.MessageResponse;
import com.example.ConnectWithWorld.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")

public class UserController {
    private  final UserService userService;

    public  UserController(UserService userService){
        this.userService = userService;
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) throws UserException{
        User user = userService.findUserById(id);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @GetMapping("username/{useranme}")
    public ResponseEntity<User> findUserByUseranme(@PathVariable String username) throws  UserException{
        User user = userService.findUserByUsername(username);
        return  new ResponseEntity<User>(user,HttpStatus.OK);
    }
    @GetMapping("/users/{userIds}")
    public ResponseEntity<List<User>> findUserByIds(@PathVariable List<Long> userIds) throws UserException {
        List<User> users = userService.findUserByIDs(userIds);
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @GetMapping("/search")
    public  ResponseEntity<List<User>> searchUserHandler(@RequestParam("q")String query) throws UserException {
        List<User> users = userService.searchUser(query);
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }
    @PutMapping("/follow/{userId}")
    public ResponseEntity<MessageResponse> followUser(@PathVariable Long userId, @RequestHeader("Authorization") String token)throws UserException{
        User user = userService.findUserByToken(token);
        String res = userService.followUser(user.getId(),userId);
        MessageResponse message = new MessageResponse(res);
        return new ResponseEntity<MessageResponse>(message,HttpStatus.OK);
    }
    @PutMapping("/unfollow/{userId}")
    public ResponseEntity<MessageResponse> unfollowUser(@PathVariable Long userId, @RequestHeader("Authorization") String token)throws UserException{
        User user = userService.findUserByToken(token);
        String res = userService.followUser(user.getId(),userId);
        MessageResponse message = new MessageResponse(res);
        return new ResponseEntity<MessageResponse>(message,HttpStatus.OK);
    }

    @GetMapping("/req")
    public ResponseEntity<User> findUserProfilebyToken(@RequestHeader("Authorization")String token) throws UserException{
        User user = userService.findUserByToken(token);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<User> updateUser(@RequestBody User user,@RequestHeader("Authorization")String token) throws UserException{
        User currentUser = userService.findUserByToken(token);
        User updateUser = userService.updateUser(user, currentUser);
        return new ResponseEntity<User>(updateUser, HttpStatus.OK);

    }
}
