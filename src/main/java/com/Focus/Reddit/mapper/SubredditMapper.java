package com.Focus.Reddit.mapper;

import com.Focus.Reddit.dto.SubredditDto;
import com.Focus.Reddit.model.Post;
import com.Focus.Reddit.model.Subreddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

    @Mapping(target = "numberOfPosts",
            expression = "java(mapPosts(subreddit.getPosts()))")
    SubredditDto mapSupreditToDto(Subreddit subreddit);


    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    Subreddit mapDtoToSubreddit(SubredditDto subredditDto);

}
