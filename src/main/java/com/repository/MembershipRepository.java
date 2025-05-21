// src/main/java/com/repository/MembershipRepository.java
package com.repository;

import com.model.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
    // Optional: find all memberships for a given user
    List<Membership> findByUserId(Long userId);
}
