package com.BusinessGuru.MentorshipManagementBackend.repository;

import com.BusinessGuru.MentorshipManagementBackend.entities.Experience;
import com.BusinessGuru.MentorshipManagementBackend.entities.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience,String> {
    List<Experience> findByUserProfile(UserProfile profile);
}
