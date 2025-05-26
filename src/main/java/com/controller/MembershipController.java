package com.controller;

import com.model.Membership;
import com.service.MembershipService;
import com.dto.MembershipDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/memberships")
public class MembershipController {

    private final MembershipService membershipSvc;

    public MembershipController(MembershipService membershipSvc) {
        this.membershipSvc = membershipSvc;
    }

    @GetMapping
    public List<Membership> list(@PathVariable Long userId) {
        // Optionally filter by userId in service if needed
        return membershipSvc.findAll();
    }

    @PostMapping
    public ResponseEntity<Membership> create(@PathVariable Long userId,
                                             @RequestBody MembershipDto dto) {
        Membership created = membershipSvc.createMembership(
                userId,
                dto.getPlanType(),
                dto.getStartDate()
        );
        return ResponseEntity.status(201).body(created);
    }

    @DeleteMapping("/{membershipId}")
    public ResponseEntity<Void> delete(@PathVariable Long membershipId) {
        membershipSvc.deleteMembership(membershipId);
        return ResponseEntity.noContent().build();
    }
}
