// src/main/java/com/repository/BookingRepository.java
package com.repository;

import com.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Optional: find all bookings for a given user
    List<Booking> findByUserId(Long userId);
}
