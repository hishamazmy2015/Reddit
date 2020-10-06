package com.Focus.Reddit.controller;

import com.Focus.Reddit.dto.CommentDto;
import com.Focus.Reddit.dto.PostRequest;
import com.Focus.Reddit.dto.PostResponse;
import com.Focus.Reddit.mapper.PostMapper;
import com.Focus.Reddit.service.CommentService;
import com.Focus.Reddit.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments/")
@AllArgsConstructor
@Slf4j
public class CommentsController {

    private final CommentService postService;
    private final PostMapper postMapper;


    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentDto commentDto) {
        postService.save(commentDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/id")
    public PostResponse getPostByID(@PathVariable Long id) {
        return postService.getPost(id);
    }


    @GetMapping("/")
    public List<PostResponse> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/by-subreddit/{id}")
    public List<PostResponse> getAllPostsBySubreddit(Long id) {
        return postService.getPostsBySubreddit(id);
    }

    @GetMapping("/by-user/{name}")
    public List<PostResponse> getPostByUserName(String username) {
        return postService.getPostByUserName(username);
    }


}
