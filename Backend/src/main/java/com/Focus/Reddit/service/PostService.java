package com.Focus.Reddit.service;

import com.Focus.Reddit.dto.PostRequest;
import com.Focus.Reddit.dto.PostResponse;
import com.Focus.Reddit.exceptions.PostNotFoundException;
import com.Focus.Reddit.exceptions.SubredditNotFoundException;
import com.Focus.Reddit.mapper.PostMapper;
import com.Focus.Reddit.model.Post;
import com.Focus.Reddit.model.Subreddit;
import com.Focus.Reddit.model.User;
import com.Focus.Reddit.repository.PostRepository;
import com.Focus.Reddit.repository.SubredditRepository;
import com.Focus.Reddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor
@Transactional
public class PostService {

    private SubredditRepository subredditRepository;
    private UserRepository userRepository;
    private PostRepository postRepository;
    private PostMapper postMapper;
    private AuthService authService;
    private MailService mailService ;

    @Transactional
    public void save(PostRequest postRequest) {
        User currentUser = authService.getCurrentUser();
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName()).orElseThrow(() -> new SubredditNotFoundException(
                postRequest.getSubredditName()));

        postRepository.save(postMapper.map(postRequest, subreddit, currentUser));


    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        return postMapper.mapPostToDto(postRepository.findById(id).
                orElseThrow(() -> new PostNotFoundException("the Post not exist")));
    }


    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream().
                map(postMapper::mapPostToDto).
                collect(toList());
    }

    public List<PostResponse> getPostsBySubreddit(Long subredditid) {

        Subreddit subreddit = subredditRepository.findById(subredditid).orElseThrow(() -> new SubredditNotFoundException(" Subreddit not exsit"));
        return postRepository.findAllBySubreddit(subreddit).stream().
                map(postMapper::mapPostToDto).collect(toList());

    }

    public List<PostResponse> getPostByUserName(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByUser(user).stream()
                .map(postMapper::mapPostToDto)
                .collect(Collectors.toList());


    }

}
