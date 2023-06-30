package com.codeup.autoconnect.repositories;

import com.codeup.autoconnect.models.Conversation;
import com.codeup.autoconnect.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    Conversation findByUser1AndUser2(User user1, User user2);

    @Query("SELECT c FROM Conversation c WHERE (c.user1.id = :userId1 AND c.user2.id = :userId2) OR (c.user1.id = :userId2 AND c.user2.id = :userId1)")
    Conversation findByUsers(@Param("userId1") Long userId1, @Param("userId2") Long userId2);

    List<Conversation> findByUser1(User currentUser);

    List<Conversation> findByUser2(User currentUser);
}
