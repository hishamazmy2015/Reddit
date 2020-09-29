package com.Focus.Reddit.service;


import com.Focus.Reddit.dto.SubredditDto;
import com.Focus.Reddit.model.Subreddit;
import com.Focus.Reddit.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;
//import java.util.stream.Collectors;

//import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditRepository subredditRepository;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit save = subredditRepository.save(mapSubredditDto(subredditDto));
        subredditDto.setId(save.getId());
        return subredditDto;
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll().stream().
                map(this::mapToDto).collect(toList());
    }

    private Subreddit mapSubredditDto(SubredditDto subredditDto) {
        return Subreddit.builder().name(subredditDto.getSubredditName()).
                description(subredditDto.getDescription()).build();
    }

    private SubredditDto mapToDto(Subreddit subreddit) {
        return SubredditDto.builder().subredditName(subreddit.getName()).
                id(subreddit.getId())
                .numberOfPosts(subreddit.getPosts().size())
                .build();
    }
}
