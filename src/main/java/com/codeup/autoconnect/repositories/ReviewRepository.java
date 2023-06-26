package com.codeup.autoconnect.repositories;

import com.codeup.autoconnect.models.Review;
import com.codeup.autoconnect.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository <Review, Long> {
    List<Review> findAllByMechanic(User mechanic);
}
