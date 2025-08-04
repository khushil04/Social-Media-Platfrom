package com.example.ConnectWithWorld.services;
import com.example.ConnectWithWorld.entities.User;
import com.example.ConnectWithWorld.exceptions.UserException;
import com.example.ConnectWithWorld.repository.PostRepository;
import com.example.ConnectWithWorld.repository.UserRepository;
import com.example.ConnectWithWorld.services.UserService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final JwtService jwtService;



    @Override
    public User findUserById(Long id) throws UserException {
        User user = userRepository.findById(id).orElseThrow(()-> new UserException("User does not exist with id: "+id));
        return user;
    }


    public User findUserByToken(String token) throws UserException{
        if(token.startsWith("Bearer")){
            token = token.substring(7);
        }

        Long id = jwtService.getIdFromToken(token);
           return userRepository.findById(id)
                .orElseThrow(()-> new UsernameNotFoundException("User not found with id:"+ id));

    }

    @Override
    public User findUserByUsername(String username) throws UserException {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UserException("User not found with username: "+ username));
        return user;
    }



    @Override
    public String followUser(Long reqUserId, Long followUserId) throws UserException{
        User reqUser = userRepository.findById(reqUserId).orElseThrow(()-> new UserException("User not found with id: "+ reqUserId));
        User followUser = userRepository.findById(followUserId).orElseThrow(()-> new UserException("User not found with id: "+ followUserId));

        if(reqUserId.equals(followUserId)){
            throw new UserException("You cannot follow yourself");
        }
        if(reqUser.getFollowing().contains(followUser)){
            throw new UserException("You already follow "+followUser.getUsername());
        }

        reqUser.getFollowing().add(followUser);
        followUser.getFollowers().add(reqUser);

        userRepository.save(reqUser);
        userRepository.save(followUser);

        return "You are now following "+followUser.getUsername();
    }

    @Override
    public String unfollowUser(Long reqUserId, Long followUserId) throws UserException {
        return "";
    }

    @Override
    public List<User> findUserByIDs(List<Long> userIds) throws UserException {
        return List.of();
    }


    public String unFollowUser(Long reqUserId, Long followUserId) throws UserException {
        User reqUser = userRepository.findById(reqUserId).orElseThrow(()-> new UserException("You do not follow "+ reqUserId));
        User unfollowUser = userRepository.findById(followUserId).orElseThrow(()-> new UserException("User not found with "+followUserId));

        if(!reqUser.getFollowing().contains(unfollowUser)){
            throw new UserException("You are not following " + unfollowUser.getUsername());
        }
        reqUser.getFollowing().remove(unfollowUser);
        unfollowUser.getFollowers().remove(reqUser);

        userRepository.save(reqUser);
        userRepository.save(unfollowUser);
        return "Unfollowed successfully";
    }


    public List<User> findUserByIds(List<Long> userIds) throws UserException{
        List<User> users = userRepository.findAllUserByUserIds(userIds);
        return users;
    }

    @Override
    public List<User> searchUser(String query) throws UserException{
        List<User> users = userRepository.findByQuery(query);
        if(users.isEmpty()){
            throw new UserException("User not found");
        }
        return users;
    }

    @Override
    public User updateUser(User updatedUser, User existingUser) throws UserException {
        if (!updatedUser.getId().equals(existingUser.getId())) {
            throw new UserException("You cannot update user");
        }
        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getBio() != null) {
            existingUser.setBio(updatedUser.getBio());
        }
        if (updatedUser.getName() != null) {
            existingUser.setName(updatedUser.getName());
        }
        if (updatedUser.getUsername() != null) {
            existingUser.setUsername(updatedUser.getUsername());
        }
        if (updatedUser.getGender() != null) {
            existingUser.setGender(updatedUser.getGender());
        }
        if (updatedUser.getMobile() != null) {
            existingUser.setMobile(updatedUser.getMobile());
        }
        if (updatedUser.getImage() != null) {
            existingUser.setImage(updatedUser.getImage());
        }
        if (updatedUser.getId().equals(existingUser.getId())) {
            userRepository.save(existingUser);
        }
        throw new UserException("You could not update user");
    }


}
