// src/main/java/com/dto/BookingDto.java
package com.dto;

import java.time.LocalDate;

public class BookingDto {
    private Long userId;
    private LocalDate bookingDate;
    // getters + setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public LocalDate getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDate bookingDate) { this.bookingDate = bookingDate; }
}
