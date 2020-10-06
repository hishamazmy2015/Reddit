package com.Focus.Reddit.repository;

import com.Focus.Reddit.model.Post;
import com.Focus.Reddit.model.User;
import com.Focus.Reddit.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
