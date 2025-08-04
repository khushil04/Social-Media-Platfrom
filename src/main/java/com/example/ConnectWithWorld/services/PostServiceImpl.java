package com.example.ConnectWithWorld.services;

import com.example.ConnectWithWorld.entities.Post;
import com.example.ConnectWithWorld.entities.User;
import com.example.ConnectWithWorld.exceptions.PostException;
import com.example.ConnectWithWorld.exceptions.UserException;
import com.example.ConnectWithWorld.repository.PostRepository;
import com.example.ConnectWithWorld.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final UserService userService;

    @Override
    @Transactional
    public Post createPost(Post post, Long userId) throws UserException {
        User user = userService.findUserById(userId);
        post.setUser(user);
        return postRepository.save(post);
    }

    @Override
    @Transactional
    public String deletePost(Long postId, Long userId) throws UserException, PostException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);
        if (post.getUser().getId().equals(user.getId())) {
            postRepository.deleteById(postId);
            return "Post deleted successfully";
        }
        throw new PostException("You cannot delete another user's post");
    }

    @Override
    public List<Post> findPostByUserId(Long userId) throws UserException {
        List<Post> posts = postRepository.findByUserId(userId);
        if (posts.isEmpty()) {
            throw new UserException("This user does not have any post");
        }
        return posts;
    }

    @Override
    public Post findPostById(Long postId) throws PostException {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostException("Post not found with id: " + postId));
    }

    // Optional extra method, not part of interface
    public List<Post> findPostById(List<Integer> postIds) throws PostException {
        List<Post> posts = postRepository.findAllById(postIds.stream().map(Long::valueOf).toList());
        if (posts.isEmpty()) {
            throw new PostException("No posts found for provided IDs");
        }
        return posts;
    }

    @Override
    public List<Post> findAllPostByUserIds(List<Long> userIds) throws PostException, UserException {
        List<Post> posts = postRepository.findAllPostByUserIds(userIds);
        if (posts.isEmpty()) {
            throw new PostException("No posts available");
        }
        return posts;
    }

    @Override
    @Transactional
    public String savePost(Long postId, Long userId) throws UserException, PostException {
        User user = userService.findUserById(userId);
        Post post = findPostById(postId);
        if (user.getSavedPost().contains(post)) {
            throw new PostException("Post is already saved");
        }
        user.getSavedPost().add(post);
        userRepository.save(user);
        return "Post saved successfully";
    }

    @Override
    @Transactional
    public String unsavedPost(Long postId, Long userId) throws UserException, PostException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        if (!user.getSavedPost().contains(post)) {
            throw new PostException("Post is not present in saved posts");
        }

        user.getSavedPost().remove(post);
        userRepository.save(user);
        return "Post removed from saved posts";
    }

    @Override
    @Transactional
    public Post likePost(Long postId, Long userId) throws UserException, PostException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        post.getLikedByUsers().add(user);
        return postRepository.save(post);
    }

    @Override
    @Transactional
    public Post unLikePost(Long postId, Long userId) throws UserException, PostException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        post.getLikedByUsers().remove(user);
        return postRepository.save(post);
    }
}
