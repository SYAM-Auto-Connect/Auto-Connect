package com.codeup.autoconnect.repositories;

import com.codeup.autoconnect.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
