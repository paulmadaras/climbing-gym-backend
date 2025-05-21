package com.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Amount paid */
    @Column(nullable = false)
    private double amount;

    /** Payment method (e.g. "CARD", "CASH") */
    @Column(nullable = false)
    private String method;

    /** Date of the payment */
    @Column(nullable = false)
    private LocalDate date;

    /** Who made this payment */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** Which booking this pays for (if any) */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", unique = true)
    private Booking booking;

    /** Which membership this pays for (if any) */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membership_id", unique = true)
    private Membership membership;

    public Payment() {}

    public Payment(double amount, String method, LocalDate date, User user) {
        this.amount = amount;
        this.method = method;
        this.date = date;
        this.user = user;
    }

    // --- Getters & Setters ---

    public Long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMethod() {
        return method;
    }
    public void setMethod(String method) {
        this.method = method;
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Booking getBooking() {
        return booking;
    }
    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Membership getMembership() {
        return membership;
    }
    public void setMembership(Membership membership) {
        this.membership = membership;
    }
}
