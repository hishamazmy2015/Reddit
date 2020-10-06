package com.Focus.Reddit.repository;

import com.Focus.Reddit.model.Comment;
import com.Focus.Reddit.model.Post;
import com.Focus.Reddit.model.User;
import com.Focus.Reddit.model.Comment;
import com.Focus.Reddit.model.Post;
import com.Focus.Reddit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
