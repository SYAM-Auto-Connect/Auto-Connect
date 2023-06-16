package com.codeup.autoconnect.repositories;

import com.codeup.autoconnect.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
