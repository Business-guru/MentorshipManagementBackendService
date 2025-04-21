package com.BusinessGuru.MentorshipManagementBackend.repository;

import com.BusinessGuru.MentorshipManagementBackend.entities.PlanUserMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanUserMapRepository extends JpaRepository<PlanUserMap,String> {
    List<PlanUserMap> findByUserId(String userId);
}
