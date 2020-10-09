package com.Focus.Reddit.service;

import com.Focus.Reddit.dto.CommentsDto;
import com.Focus.Reddit.dto.PostRequest;
import com.Focus.Reddit.dto.PostResponse;
import com.Focus.Reddit.exceptions.PostNotFoundException;
import com.Focus.Reddit.exceptions.SubredditNotFoundException;
import com.Focus.Reddit.mapper.CommentMapper;
import com.Focus.Reddit.mapper.PostMapper;
import com.Focus.Reddit.model.*;
import com.Focus.Reddit.repository.CommentRepository;
import com.Focus.Reddit.repository.PostRepository;
import com.Focus.Reddit.repository.SubredditRepository;
import com.Focus.Reddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@org.springframework.transaction.annotation.Transactional
public class CommentService {

    private static final String POST_URL = "";
    private final SubredditRepository subredditRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;

    private final AuthService authService;
    private final MailService mailService;

    private final MailContentBuilder mailContainBuilder;

    @Transactional
    public void save(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Not eXist"));
        User currentUser = authService.getCurrentUser();
        Comment comment = commentMapper.map(commentsDto, post, currentUser);

        commentRepository.save(comment);

        String message = mailContainBuilder.build(currentUser + "  post " + POST_URL);
        sendMailNotification(message, post.getUser());
    }

    private void sendMailNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername(), user.getEmail(), message));
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        return postMapper.mapPostToDto(postRepository.findById(id).
                orElseThrow(() -> new PostNotFoundException("the Post not exist")));
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

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<CommentsDto> getAllCommentsForPosts(Long postId) {
        Post byId = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return
                commentRepository.findByPost(byId).stream().
                        map(commentMapper::mapToDto).
                        collect(toList());
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<CommentsDto> getAllCommentsForUser(Long userId) {
        User byId = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException(userId.toString()));
        return
                commentRepository.findAllByUser(byId).stream().
                        map(commentMapper::mapToDto).
                        collect(toList());
    }

}
