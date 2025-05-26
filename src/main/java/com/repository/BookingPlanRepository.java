// com/repository/BookingPlanRepository.java
package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.model.BookingPlanImpl;
import com.model.BookingPlan;
import java.util.Optional;

public interface BookingPlanRepository
        extends JpaRepository<BookingPlanImpl, Long> {

    Optional<BookingPlanImpl> findTopByOrderByIdAsc();
}
