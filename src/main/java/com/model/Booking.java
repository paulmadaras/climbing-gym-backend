package com.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The day the user booked */
    @Column(nullable = false)
    private LocalDate bookingDate;

    @Column(nullable = false)
    private BookingPlan plan;

    @Column(nullable = false)
    private double price;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private Payment payment;

    /** Who made this booking */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // --- Constructors ---

    public Booking() {}

    public Booking(User user, LocalDate bookingDate, BookingPlan plan, double price) {
        this.bookingDate = bookingDate;
        this.user = user;
        this.plan = plan;
        this.price = price;
    }

    // --- Getters & Setters ---


    public Long getId() {
        return id;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getUserId() {
        return this.user.getId();
    }

    public BookingPlan getPlan() {
        return plan;
    }

    public void setPlan(BookingPlan plan) {
        this.plan = plan;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
