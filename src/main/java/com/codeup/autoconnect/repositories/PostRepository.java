package com.codeup.autoconnect.repositories;

import com.codeup.autoconnect.models.Post;
import com.codeup.autoconnect.models.Review;
import com.codeup.autoconnect.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUser(User user);
}