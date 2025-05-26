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

    public DataInitializer(
            UserService userService,
            BookingService bookingService,
            MembershipService membershipService
    ) {
        this.userService = userService;
        this.bookingService = bookingService;
        this.membershipService = membershipService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedUsers();
        seedBookingsAndMemberships();
    }

    private void seedUsers(){

        // only seed if no users exist
        if (userService.findAll().isEmpty()) {

            // 1) Default password for all users
            final String defaultPassword = "Pass123!";

            // 2) Seed climber/student users from parallel lists
            List<String> firstNames = List.of("Paul", "Rafael Dorian", "Maria Eliza", "Calina Annemary", "Felix", "Mihai Ionut");
            List<String> lastNames = List.of("Iordache", "Butas", "Gozaman-Pop", "Borzan", "Dumitrescu", "Leo");
            String domain = "example.com";

            for (int i = 0; i < firstNames.size(); i++) {
                String first = firstNames.get(i);
                String last = lastNames.get(i).replaceAll("-", " ");
                String username = first.toLowerCase() + last.toLowerCase();
                String email = (username.replace(" ", "_") + "@" + domain).toLowerCase();

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
        }
    }

    private void seedBookingsAndMemberships() {

        // get all climbers in insertion order
        List<User> climbers = userService.findAll().stream()
                .filter(u -> u.getRole() == Role.CLIMBER)
                .toList();


        if (climbers.isEmpty()) return;

        int half = (climbers.size() + 1) / 2;  // odd→first half slightly larger

        for (int i = 0; i < climbers.size(); i++) {
            User c = climbers.get(i);

            if (i < half) {
                // FIRST HALF → membership
                membershipService.createMembership(
                        c.getId(),
                        MembershipPlan.EIGHT,
                        LocalDate.now()
                );
            } else {
                // SECOND HALF → simple booking (today)
                bookingService.createBooking(
                        c.getId(),
                        LocalDate.now()
                );
            }
        }
    }
}
