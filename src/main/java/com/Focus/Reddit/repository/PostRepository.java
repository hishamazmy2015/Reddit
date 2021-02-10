package com.Focus.Reddit.repository;

import com.Focus.Reddit.model.Post;
import com.Focus.Reddit.model.Subreddit;
import com.Focus.Reddit.model.Post;
import com.Focus.Reddit.model.Subreddit;
import com.Focus.Reddit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUser(User user);
}
