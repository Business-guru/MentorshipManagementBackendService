package com.BusinessGuru.MentorshipManagementBackend.repository;

import com.BusinessGuru.MentorshipManagementBackend.entities.MentorshipPlan;
import com.BusinessGuru.MentorshipManagementBackend.entities.UserProfile;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<MentorshipPlan,String> {
    List<MentorshipPlan> findByCreatedBy(UserProfile userProfile);
}
