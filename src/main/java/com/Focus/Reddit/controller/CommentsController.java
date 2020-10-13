package com.Focus.Reddit.controller;

import com.Focus.Reddit.dto.CommentsDto;
import com.Focus.Reddit.dto.PostResponse;
import com.Focus.Reddit.mapper.PostMapper;
import com.Focus.Reddit.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/comments/")
@AllArgsConstructor
@Slf4j
public class CommentsController {

    private final CommentService commentService;
    private final PostMapper postMapper;


    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentsDto commentDto) {
        commentService.save(commentDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForPosts(@PathVariable Long postId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsForPosts(postId));
    }

    @GetMapping("/by-user/{userName}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForUSer(@PathVariable String userName) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsForUser(userName));
    }


}
