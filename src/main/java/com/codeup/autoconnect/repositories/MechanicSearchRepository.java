package com.codeup.autoconnect.repositories;

import com.codeup.autoconnect.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface MechanicSearchRepository extends JpaRepository<User,Long> {


    @Query("from User where isMechanic = true")
    List<User> findAllMechanicUsers();
//    @Query("from User where address_zip = ?1")
//    @Query("from User where address_zip LIKE %?1% OR address_state LIKE %?1% OR address_city LIKE %?1%")
    @Query("from User where address_zip LIKE CONCAT('%', ?1, '%') OR address_state LIKE CONCAT('%', ?1, '%') OR address_city LIKE CONCAT('%', ?1, '%') AND isMechanic = true")

    List<User> findAllByAddressZip(String zipcode);

}




