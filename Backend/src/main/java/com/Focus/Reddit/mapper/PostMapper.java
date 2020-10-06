package com.Focus.Reddit.mapper;

import com.Focus.Reddit.dto.PostRequest;
import com.Focus.Reddit.dto.PostResponse;
import com.Focus.Reddit.dto.SubredditDto;
import com.Focus.Reddit.model.Post;
import com.Focus.Reddit.model.Subreddit;
import com.Focus.Reddit.model.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {


    @Mapping(target = "createdDate", expression = "java(java.time.Instance.now())")
    @Mapping(target = "subreddit",source = "subreddit")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "description", source = "postRequest.description")
    public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);


    @Mapping(target = "id", source = "postId")
    @Mapping(target = "postName",source = "postName")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    PostResponse mapPostToDto(Post post);


}
