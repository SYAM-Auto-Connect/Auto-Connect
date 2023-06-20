package com.codeup.autoconnect.repositories;

import com.codeup.autoconnect.models.Appointment;
import com.codeup.autoconnect.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAppointmentsByRequesterId(long userId);

}
