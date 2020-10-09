package com.Focus.Reddit.service;

import com.Focus.Reddit.dto.VoteDto;
import com.Focus.Reddit.exceptions.PostNotFoundException;
import com.Focus.Reddit.exceptions.SpringRedditException;
import com.Focus.Reddit.model.Post;
import com.Focus.Reddit.model.User;
import com.Focus.Reddit.model.Vote;
import com.Focus.Reddit.repository.PostRepository;
import com.Focus.Reddit.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.Focus.Reddit.model.VoteType.UPVOTE;


@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;


    public void save(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId()).orElseThrow(() -> new PostNotFoundException("Post Not Found with ID :  "+voteDto.getPostId()));
        User currentUser = authService.getCurrentUser();
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,currentUser );

        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
            throw new SpringRedditException("You have already " +
                    voteDto.getVoteType() + "'d for this Post");
        }
        if (UPVOTE.equals(voteDto.getVoteType()) )
            post.setVoteCount(post.getVoteCount()+1);
        else
            post.setVoteCount(post.getVoteCount()-1);

        voteRepository.save(mapToVote(voteDto,post));
        postRepository.save(post);



    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
