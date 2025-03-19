package com.BusinessGuru.MentorshipManagementBackend.repository;

import com.BusinessGuru.MentorshipManagementBackend.entities.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile,String> {
    Optional<UserProfile> findByUserId(String userId);
}
