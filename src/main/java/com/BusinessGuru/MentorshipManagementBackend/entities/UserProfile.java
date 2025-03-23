package com.BusinessGuru.MentorshipManagementBackend.entities;

import com.BusinessGuru.MentorshipManagementBackend.commons.BaseEntity;
import com.BusinessGuru.MentorshipManagementBackend.enums.UserType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @ElementCollection
    private List<String> skills;

    private String about;

    private String bio;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL)
    private List<Experience> experienceList;

    private Double avgRating; // only for mentor

    private Boolean isProfileCompleted;
}
