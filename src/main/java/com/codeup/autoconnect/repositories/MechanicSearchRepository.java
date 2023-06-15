package com.codeup.autoconnect.repositories;

import com.codeup.autoconnect.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MechanicSearchRepository extends JpaRepository<User,Long> {

//    @Query("SELECT * FROM users WHERE isMechanic = true")
//    User getAllMechanicById(Long id);

    @Query("from User where isMechanic = true")
    List<User> findAllMechanicUsers();


}
