package com.model;

import javax.persistence.*;

@Entity
@Table(name = "booking_plan")
public class BookingPlanImpl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Base price for a single climb session (without shoes) */
    @Column(nullable = false)
    private double basePrice;

    /** Price to rent shoes for one session */
    @Column(nullable = false)
    private double shoesPrice;

    /** Discount rate (e.g. 0.2 = 20%) applied to student plans */
    @Column(nullable = false)
    private double discountRate;

    public BookingPlanImpl() { }

    public BookingPlanImpl(double basePrice, double shoesPrice, double discountRate) {
        this.basePrice = basePrice;
        this.shoesPrice = shoesPrice;
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

    public double getShoesPrice() {
        return shoesPrice;
    }
    public void setShoesPrice(double shoesPrice) {
        this.shoesPrice = shoesPrice;
    }

    public double getDiscountRate() {
        return discountRate;
    }
    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

}
