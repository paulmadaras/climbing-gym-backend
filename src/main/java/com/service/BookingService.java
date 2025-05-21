// com/service/BookingService.java
package com.service;

import com.model.Booking;
import com.model.User;
import com.repository.BookingRepository;
import com.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import com.repository.BookingPlanRepository;
import com.model.BookingPlan;
import com.model.BookingPlanImpl;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BookingService {

    private final BookingPlanRepository bookingPlanRepo;
    private final BookingRepository bookingRepo;
    private final UserRepository userRepo;

    public BookingService(BookingPlanRepository bookingPlanRepo,
                          BookingRepository bookingRepo,
                          UserRepository userRepo) {
        this.bookingPlanRepo = bookingPlanRepo;
        this.bookingRepo = bookingRepo;
        this.userRepo = userRepo;
    }

    /** List all configured booking plans */
    public List<BookingPlanImpl> getAllBookingPlans() {
        return bookingPlanRepo.findAll();
    }

    /** Fetch a single plan by enum type */
    public BookingPlanImpl getBookingPlan(BookingPlan planType) {
        return bookingPlanRepo.findByPlanType(planType)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Booking plan not found: " + planType));
    }

    /** Calculate the price for a given plan */
    public double calculatePrice(BookingPlan planType) {
        BookingPlanImpl plan = getBookingPlan(planType);
        return plan.getPrice();
    }

    public List<Booking> findAll() {
        return bookingRepo.findAll();
    }

    public Booking findById(Long id) {
        return bookingRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found: " + id));
    }

    public Booking createBooking(Long userId, LocalDate bookingDate) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user ID: " + userId));
        Booking booking = new Booking(bookingDate, user);
        return bookingRepo.save(booking);
    }

    public void deleteBooking(Long id) {
        if (!bookingRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found: " + id);
        }
        bookingRepo.deleteById(id);
    }
}
