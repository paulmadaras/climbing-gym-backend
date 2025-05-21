// src/main/java/com/dto/MembershipDto.java
package com.dto;

import com.model.MembershipPlan;
import java.time.LocalDate;

public class MembershipDto {
    private Long userId;
    private MembershipPlan planType;
    private LocalDate startDate;
    private LocalDate endDate;
    // getters + setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public MembershipPlan getPlanType() { return planType; }
    public void setPlanType(MembershipPlan planType) { this.planType = planType; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
}
