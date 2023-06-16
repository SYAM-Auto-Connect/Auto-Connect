package com.codeup.autoconnect.repositories;

import com.codeup.autoconnect.models.Comment;
import com.codeup.autoconnect.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
