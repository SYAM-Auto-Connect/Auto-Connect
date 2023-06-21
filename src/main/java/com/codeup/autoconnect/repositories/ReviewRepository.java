package com.codeup.autoconnect.repositories;

import com.codeup.autoconnect.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository <Review, Long> {
}
