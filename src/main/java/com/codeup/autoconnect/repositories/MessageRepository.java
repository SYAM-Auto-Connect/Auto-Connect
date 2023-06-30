package com.codeup.autoconnect.repositories;

import com.codeup.autoconnect.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
