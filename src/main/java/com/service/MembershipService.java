package com.service;

import com.model.*;
import com.repository.BookingRepository;
import com.repository.MembershipPlanRepository;
import com.repository.MembershipRepository;
import com.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class MembershipService {

    private final MembershipPlanRepository membershipPlanRepo;
    private final MembershipRepository membershipRepo;
    private final UserRepository userRepo;
    private final BookingRepository bookingRepo;

    public MembershipService(MembershipPlanRepository membershipPlanRepo,
                             MembershipRepository membershipRepo,
                             UserRepository userRepo, BookingRepository bookingRepo) {
        this.membershipPlanRepo = membershipPlanRepo;
        this.membershipRepo = membershipRepo;
        this.userRepo = userRepo;
        this.bookingRepo = bookingRepo;
    }

    public List<Membership> findAll() {
        return membershipRepo.findAll();
    }

    public Membership findById(Long id) {
        return membershipRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Membership not found: " + id));
    }

    public Membership createMembership(
            Long userId,
            MembershipPlan planType,
            LocalDate startDate
    ) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Invalid user ID: " + userId));

        // **Validation: user must NOT have a booking on the membership startDate**
        boolean hasBookingSameDay = bookingRepo
                .findByUserId(userId)
                .stream()
                .anyMatch(b -> b.getBookingDate().isEqual(startDate));
        if (hasBookingSameDay) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cannot create membership: user has a booking on " + startDate
            );
        }

        Membership m = new Membership();
        m.setUser(user);
        m.setPlanType(planType);
        m.setStartDate(startDate);
        m.setEndDate();
        return membershipRepo.save(m);
    }

    public void deleteMembership(Long id) {
        if (!membershipRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Membership not found: " + id);
        }
        membershipRepo.deleteById(id);
    }

    /** Fetch a single plan by enum type */
    public MembershipPlanImpl getConfig() {
        return membershipPlanRepo.findAll().stream().findFirst().
                orElseThrow(() -> new IllegalArgumentException(
                        "No config found. Please create a booking plan first."));
    }

    /**
     * Calculate the price for any MembershipPlan,
     * using the single config values.
     */
    public double calculatePrice(MembershipPlan planType) {
        MembershipPlanImpl cfg = getConfig(); // Assuming a similar getConfig() for MembershipPlanImpl exists
        switch (planType) {
            case EVERYDAY:
                return cfg.getBasePrice();
            case EIGHT:
                return cfg.getEightPrice();
            case STUDENT:
                return cfg.getBasePrice() * (1 - cfg.getDiscountRate());
            case STUDENT_EIGHT:
                return cfg.getEightPrice() * (1 - cfg.getDiscountRate());
            default:
                throw new IllegalArgumentException("Unknown membership plan: " + planType);
        }
    }
}
