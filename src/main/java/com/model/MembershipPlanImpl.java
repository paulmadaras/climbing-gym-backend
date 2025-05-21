package com.model;

import javax.persistence.*;

@Entity
@Table(name = "membership_plan")
public class MembershipPlanImpl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private MembershipPlan planType;

    private double basePrice;
    private double eightPrice;
    private double discountRate; // e.g. 0.2 = 20%

    public MembershipPlanImpl() {} // JPA

    // getters & setters...

    public double getPrice() {
        switch (planType) {
            case EVERYDAY:
                return basePrice;
            case EIGHT:
                return eightPrice;
            case STUDENT:
                return basePrice * (1 - discountRate);
            case STUDENT_EIGHT:
                return eightPrice * (1 - discountRate);
            default:
                throw new IllegalArgumentException("Unknown plan: " + planType);
        }
    }
}
