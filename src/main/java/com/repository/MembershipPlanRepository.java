package com.repository;

import com.model.MembershipPlan;
import com.model.MembershipPlanImpl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MembershipPlanRepository
        extends JpaRepository<MembershipPlanImpl, Long> {
}
