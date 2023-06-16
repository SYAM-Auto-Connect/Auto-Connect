package com.codeup.autoconnect.repositories;

import com.codeup.autoconnect.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
