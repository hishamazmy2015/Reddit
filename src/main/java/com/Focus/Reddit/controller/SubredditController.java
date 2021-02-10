package com.Focus.Reddit.controller;

import com.Focus.Reddit.dto.SubredditDto;
import com.Focus.Reddit.service.SubredditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
@Slf4j
public class SubredditController {

    private final SubredditService subredditService;

    @PostMapping
    public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditDto subredditDto) {
        System.out.println("subredditDto "+subredditDto);

        return ResponseEntity.status(HttpStatus.CREATED).
                body(subredditService.save(subredditDto));
    }
 
//    @GetMapping("/")
//    public ResponseEntity<List<SubredditDto>> getAllSubreddits() {
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(subredditService.getAll());
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<SubredditDto> createSubreddit(@PathVariable Long id) {
//        return ResponseEntity.status(HttpStatus.CREATED).
//                body(subredditService.getSubredit(id));
//
//
//    }


}
