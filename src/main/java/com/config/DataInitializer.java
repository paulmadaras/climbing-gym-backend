package com.config;

import com.model.*;
import com.dto.BookingDto;
import com.dto.MembershipDto;
import com.dto.PaymentDto;
import com.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final BookingService bookingService;
    private final MembershipService membershipService;
    private final PaymentService paymentService;

    public DataInitializer(
            UserService userService,
            BookingService bookingService,
            MembershipService membershipService,
            PaymentService paymentService
    ) {
        this.userService = userService;
        this.bookingService = bookingService;
        this.membershipService = membershipService;
        this.paymentService = paymentService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedUsersAndTransactions();
    }

    private void seedUsersAndTransactions() {
        // only seed if no users exist
        if (userService.findAll().isEmpty()) {

            // 1) Default password for all users
            final String defaultPassword = "Pass123!";

            // 2) Seed climber/student users from parallel lists
            List<String> firstNames = List.of("Paul", "Alice", "Bob");
            List<String> lastNames  = List.of("Iordache", "Smith", "Jones");
            String domain = "example.com";

            for (int i = 0; i < firstNames.size(); i++) {
                String first = firstNames.get(i);
                String last  = lastNames.get(i);
                String username = first.toLowerCase() + last.toLowerCase();
                String email = (username + "@" + domain).toLowerCase();

                User climber = new User(
                        first,
                        last,
                        username,
                        email,
                        defaultPassword,
                        Role.CLIMBER
                );
                climber.setStudent(true);
                climber.setStudentVerified(false); // assume proof handled elsewhere
                userService.create(climber);
            }

            // 3) Seed one staff
            User staff = new User(
                    "Rares",
                    "Popescu",
                    "rarespopescu",
                    "staff1@" + domain,
                    defaultPassword,
                    Role.STAFF
            );
            staff.setStudent(false);
            userService.create(staff);

            // 4) Seed one admin
            User admin = new User(
                    "Paul",
                    "Madaras",
                    "paulmadaras",
                    "admin@" + domain,
                    defaultPassword,
                    Role.ADMIN
            );
            admin.setStudent(false);
            userService.create(admin);

            // 5) Pick the first climber to make a booking & membership
            User climber1 = userService.findAll().stream()
                    .filter(u -> u.getRole() == Role.CLIMBER)
                    .findFirst()
                    .orElseThrow();

            // 6) Create a booking for tomorrow
            Booking booking = bookingService.createBooking(climber1.getId(), LocalDate.now());

            // 7) Create a membership for the next month
            Membership membership = membershipService.createMembership(
                    climber1.getId(),
                    MembershipPlan.EIGHT, // 8-days-a-month plan
                    LocalDate.now()
            );

            // 8) Record a payment for the booking
            PaymentDto p1 = new PaymentDto();
            p1.setUserId(climber1.getId());
            p1.setBookingId(booking.getId());
            p1.setAmount(bookingService.calculatePrice(BookingPlan.NORMAL));
            p1.setMethod("CARD");
            p1.setDate(LocalDate.now());
            paymentService.create(p1);

            // 9) Record a payment for the membership
            PaymentDto p2 = new PaymentDto();
            p2.setUserId(climber1.getId());
            p2.setMembershipId(membership.getId());
            p2.setAmount(membershipService
                    .findById(membership.getId())
                    .getPlanType() == MembershipPlan.EIGHT
                    ? membershipService.findById(membership.getId()).getPrice()
                    : 0.0
            );
            p2.setMethod("CARD");
            p2.setDate(LocalDate.now());
            paymentService.create(p2);
        }
    }
}
