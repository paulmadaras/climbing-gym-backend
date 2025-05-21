package com.controller;

import com.model.Booking;
import com.service.BookingService;
import com.dto.BookingDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/bookings")
public class BookingController {

    private final BookingService bookingSvc;

    public BookingController(BookingService bookingSvc) {
        this.bookingSvc = bookingSvc;
    }

    @GetMapping
    public List<Booking> list(@PathVariable Long userId) {
        // Optionally filter by userId in service if needed
        return bookingSvc.findAll();
    }

    @PostMapping
    public ResponseEntity<Booking> create(@PathVariable Long userId,
                                          @RequestBody BookingDto dto) {
        Booking created = bookingSvc.createBooking(userId, dto.getBookingDate());
        return ResponseEntity.status(201).body(created);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> delete(@PathVariable Long bookingId) {
        bookingSvc.deleteBooking(bookingId);
        return ResponseEntity.noContent().build();
    }
}
