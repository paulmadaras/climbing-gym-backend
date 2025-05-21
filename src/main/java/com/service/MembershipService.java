package com.service;

import com.model.Membership;
import com.model.MembershipPlan;
import com.model.MembershipPlanImpl;
import com.model.User;
import com.repository.MembershipPlanRepository;
import com.repository.MembershipRepository;
import com.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class MembershipService {

    private final MembershipPlanRepository membershipPlanRepo;
    private final MembershipRepository membershipRepo;
    private final UserRepository userRepo;

    public MembershipService(MembershipPlanRepository membershipPlanRepo,
                             MembershipRepository membershipRepo,
                             UserRepository userRepo) {
        this.membershipPlanRepo = membershipPlanRepo;
        this.membershipRepo = membershipRepo;
        this.userRepo = userRepo;
    }

    public List<Membership> findAll() {
        return membershipRepo.findAll();
    }

    public Membership findById(Long id) {
        return membershipRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Membership not found: " + id));
    }

    public Membership createMembership(Long userId,
                                       MembershipPlan planType,
                                       LocalDate startDate,
                                       LocalDate endDate) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user ID: " + userId));
        Membership m = new Membership();
        m.setUser(user);
        m.setPlanType(planType);
        m.setStartDate(startDate);
        m.setEndDate(endDate);
        return membershipRepo.save(m);
    }

    public void deleteMembership(Long id) {
        if (!membershipRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Membership not found: " + id);
        }
        membershipRepo.deleteById(id);
    }

    public double calculatePrice(Membership membership) {
        MembershipPlanImpl config = membershipPlanRepo
                .findByPlanType(membership.getPlanType())
                .orElseThrow(() -> new IllegalStateException(
                        "No pricing configured for " + membership.getPlanType()));
        return config.getPrice();
    }
}
