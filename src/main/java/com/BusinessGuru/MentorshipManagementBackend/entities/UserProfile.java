package com.BusinessGuru.MentorshipManagementBackend.entities;

import com.BusinessGuru.MentorshipManagementBackend.commons.BaseEntity;
import com.BusinessGuru.MentorshipManagementBackend.enums.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    private String userId; // to sync with auth service

    private String firstName;

    private String lastName;

    private String email;

    private Set<String> skills;

    private String about;

    private String bio;

    private UserType userType;

    @OneToMany
    private List<Experience> experienceList;

    private BigDecimal avgRating; // only for mentor

    private Boolean isProfileCompleted;
}
