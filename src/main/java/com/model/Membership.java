package com.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private MembershipPlan planType;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @OneToOne(mappedBy = "membership", cascade = CascadeType.ALL)
    private Payment payment;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Membership() {
    }

    public Membership(MembershipPlan planType, LocalDate startDate, User user) {
        this.planType = planType;
        this.startDate = startDate;
        this.endDate = startDate.plusMonths(1);
        this.user = user;
    }


    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MembershipPlan getPlanType() {
        return planType;
    }

    public void setPlanType(MembershipPlan planType) {
        this.planType = planType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
