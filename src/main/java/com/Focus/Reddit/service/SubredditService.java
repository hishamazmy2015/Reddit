package com.Focus.Reddit.service;


import com.Focus.Reddit.dto.SubredditDto;
import com.Focus.Reddit.exceptions.SpringRedditException;
import com.Focus.Reddit.mapper.SubredditMapper;
import com.Focus.Reddit.model.Subreddit;
import com.Focus.Reddit.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final SubredditMapper subredditMapper;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit save = subredditRepository
                .save(subredditMapper.mapDtoToSubreddit(subredditDto));
        System.out.println("save "+save);
        System.out.println("subredditDto "+subredditDto);

        subredditDto.setId(save.getId());
        return subredditDto;
    }

//    @org.springframework.transaction.annotation.Transactional(readOnly = true)
//    public List<SubredditDto> getAll() {
//        return subredditRepository.findAll().stream().
//                map(subredditMapper::mapSupreditToDto).collect(toList());
//    }

//    @org.springframework.transaction.annotation.Transactional(readOnly = true)
//    public SubredditDto getSubredit(Long id) {
//        Subreddit subredit = subredditRepository.findById(id).orElseThrow(() -> new SpringRedditException("No Subredit found"));
//        return subredditMapper.mapSupreditToDto(subredit);
//    }

//    private Subreddit mapSubredditDto(SubredditDto subredditDto) {
//        return Subreddit.builder().name(subredditDto.getSubredditName()).
//                description(subredditDto.getDescription()).build();
//    }

//    private SubredditDto mapToDto(Subreddit subreddit) {
//        return SubredditDto.builder().subredditName(subreddit.getName()).
//                id(subreddit.getId())
//                .numberOfPosts(subreddit.getPosts().size())
//                .build();
//    }
}
