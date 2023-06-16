package com.codeup.autoconnect.repositories;

import com.codeup.autoconnect.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface MechanicSearchRepository extends JpaRepository<User,Long> {


    @Query("from User where isMechanic = true")
    List<User> findAllMechanicUsers();


}




