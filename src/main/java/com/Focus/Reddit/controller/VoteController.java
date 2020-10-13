package com.Focus.Reddit.controller;

import com.Focus.Reddit.dto.CommentsDto;
import com.Focus.Reddit.dto.VoteDto;
import com.Focus.Reddit.mapper.PostMapper;
import com.Focus.Reddit.service.CommentService;
import com.Focus.Reddit.service.VoteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/votes/")
@AllArgsConstructor
@Slf4j
public class VoteController {

    private final VoteService voteService;


    @PostMapping
    public ResponseEntity<Void> createVote(@RequestBody VoteDto voteDto) {
        voteService.save(voteDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }



}
