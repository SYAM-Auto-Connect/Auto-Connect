package com.codeup.autoconnect.repository;

import com.codeup.autoconnect.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> {
    User findByUsername(String username);
}
