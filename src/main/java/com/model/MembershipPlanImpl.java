package com.model;

import javax.persistence.*;

@Entity
@Table(name = "membership_plan")
public class MembershipPlanImpl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double basePrice;
    private double eightPrice;
    private double discountRate; // e.g. 0.2 = 20%

    public MembershipPlanImpl() {

    }

    public MembershipPlanImpl(double basePrice, double eightPrice, double discountRate) {
        this.basePrice = basePrice;
        this.eightPrice = eightPrice;
        this.discountRate = discountRate;
    }

    public Long getId() {
        return id;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getEightPrice() {
        return eightPrice;
    }

    public void setEightPrice(double eightPrice) {
        this.eightPrice = eightPrice;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }
}
