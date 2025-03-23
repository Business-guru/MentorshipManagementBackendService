package com.BusinessGuru.MentorshipManagementBackend.repository;

import com.BusinessGuru.MentorshipManagementBackend.entities.UserProfile;
import com.BusinessGuru.MentorshipManagementBackend.enums.UserType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

//@Repository
//public interface UserProfileRepository extends JpaRepository<UserProfile,String> {
//    Optional<UserProfile> findByUserId(String userId);
//
//    List<UserProfile> findByUserType(UserType userType);
//
//
//}


@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, String> {
    Optional<UserProfile> findByUserId(String userId);

    Page<UserProfile> findByUserType(UserType userType, Pageable pageable);


    @Query("SELECT u FROM UserProfile u JOIN u.skills s WHERE u.userType = :userType AND LOWER(s) IN :skills")
    Page<UserProfile> findByUserTypeAndSkillsIn(@Param("userType") UserType userType,
                                                @Param("skills") List<String> skills,
                                                Pageable pageable);

    @Query("SELECT u FROM UserProfile u WHERE u.userType = :userType " +
            "AND (LOWER(u.firstName) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(u.about) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR EXISTS (SELECT s FROM u.skills s WHERE LOWER(s) LIKE LOWER(CONCAT('%', :query, '%'))))")
    Page<UserProfile> searchMentors(@Param("query") String query, @Param("userType") UserType userType, Pageable pageable);

}
