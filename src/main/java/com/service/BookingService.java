// com/service/BookingService.java
package com.service;

import com.model.*;
import com.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.server.ResponseStatusException;

@Service
public class BookingService {

    private final MembershipRepository membershipRepo;
    private final BookingPlanRepository bookingPlanRepo;
    private final BookingRepository bookingRepo;
    private final UserRepository userRepo;

    public BookingService(MembershipRepository membershipRepo, BookingPlanRepository bookingPlanRepo,
                          BookingRepository bookingRepo,
                          UserRepository userRepo) {
        this.membershipRepo = membershipRepo;
        this.bookingPlanRepo = bookingPlanRepo;
        this.bookingRepo = bookingRepo;
        this.userRepo = userRepo;
    }

    /** Fetch a single plan by enum type */
    public BookingPlanImpl getConfig() {
        return bookingPlanRepo.findAll().stream().findFirst().
                orElseThrow(() -> new IllegalArgumentException(
                        "No config found. Please create a booking plan first."));
    }

    /**
     * Calculate the price for any BookingPlan,
     * using the single config values.
     */
    public double calculatePrice(BookingPlan planType) {
        BookingPlanImpl cfg = getConfig();
        switch (planType) {
            case NORMAL:
                return cfg.getBasePrice();
            case STUDENT:
                return cfg.getBasePrice() * (1 - cfg.getDiscountRate());
            case NORMAL_PLUS_SHOES:
                return cfg.getBasePrice() + cfg.getShoesPrice();
            case STUDENT_PLUS_SHOES:
                return (cfg.getBasePrice() + cfg.getShoesPrice()) * (1 - cfg.getDiscountRate());
            case JUST_SHOES:
                return cfg.getShoesPrice();
            default:
                throw new IllegalArgumentException("Unknown plan: " + planType);
        }
    }

    public List<Booking> findAll() {
        return bookingRepo.findAll();
    }

    public Booking findById(Long id) {
        return bookingRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found: " + id));
    }

    public Booking createBooking(Long userId, BookingPlan plan, LocalDate bookingDate) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Invalid user ID: " + userId));

        // **Validation: user must NOT have an active membership on bookingDate**
        boolean hasActiveMembership = membershipRepo
                .findByUser_Id(userId)
                .stream()
                .anyMatch(m ->
                        !m.getStartDate().isAfter(bookingDate) &&
                                !m.getEndDate().isBefore(bookingDate)
                );
        if (hasActiveMembership) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cannot book: user has an active membership covering " + bookingDate
            );
        }

        Booking booking = new Booking(user, bookingDate, plan, calculatePrice(plan));
        return bookingRepo.save(booking);
    }

    public void deleteBooking(Long id) {
        if (!bookingRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found: " + id);
        }
        bookingRepo.deleteById(id);
    }

    /**
     * Creates or updates the BookingPlanConfig.
     *
     * @param basePrice    full‐day price
     * @param discountRate fraction between 0 and 1
     * @param shoesPrice   rental fee for shoes
     * @return the saved config entity
     */
    public BookingPlanImpl updateConfig(double basePrice,
                                          double discountRate,
                                          double shoesPrice) {
        BookingPlanImpl cfg = bookingPlanRepo.findTopByOrderByIdAsc()
                .orElseGet(BookingPlanImpl::new);

        cfg.setBasePrice(basePrice);
        cfg.setDiscountRate(discountRate);
        cfg.setShoesPrice(shoesPrice);

        return bookingPlanRepo.save(cfg);
    }
}
