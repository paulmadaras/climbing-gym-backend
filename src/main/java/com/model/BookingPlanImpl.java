package com.model;

import javax.persistence.*;

@Entity
@Table(name = "booking_plan")
public class BookingPlanImpl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The type of booking plan (e.g. NORMAL, STUDENT_PLUS_SHOES, etc.) */
    @Enumerated(EnumType.STRING)
    @Column(name = "plan_type", unique = true, nullable = false)
    private BookingPlan planType;

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

    public BookingPlanImpl(BookingPlan planType, double basePrice, double shoesPrice, double discountRate) {
        this.planType = planType;
        this.basePrice = basePrice;
        this.shoesPrice = shoesPrice;
        this.discountRate = discountRate;
    }

    public Long getId() {
        return id;
    }

    public BookingPlan getPlanType() {
        return planType;
    }
    public void setPlanType(BookingPlan planType) {
        this.planType = planType;
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

    /**
     * Calculate the effective price for this plan:
     * - NORMAL: basePrice
     * - STUDENT: basePrice * (1 - discountRate)
     * - NORMAL_PLUS_SHOES: basePrice + shoesPrice
     * - STUDENT_PLUS_SHOES: basePrice * (1 - discountRate) + shoesPrice
     * - JUST_SHOES: shoesPrice
     */
    public double getPrice() {
        switch (planType) {
            case NORMAL:
                return basePrice;
            case STUDENT:
                return basePrice * (1 - discountRate);
            case NORMAL_PLUS_SHOES:
                return basePrice + shoesPrice;
            case STUDENT_PLUS_SHOES:
                return basePrice * (1 - discountRate) + shoesPrice;
            case JUST_SHOES:
                return shoesPrice;
            default:
                throw new IllegalArgumentException("Unknown booking plan: " + planType);
        }
    }
}
