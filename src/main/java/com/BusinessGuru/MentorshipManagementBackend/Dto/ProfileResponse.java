package com.BusinessGuru.MentorshipManagementBackend.Dto;

import com.BusinessGuru.MentorshipManagementBackend.entities.Experience;
import com.BusinessGuru.MentorshipManagementBackend.enums.UserType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponse {
    private String id;

    private String userId; // to sync with auth service

    private String firstName;

    private String lastName;

    private String email;

    private Set<String> skills;

    private String about;

    private String bio;

    private UserType userType;

    private List<ExperienceReponse> experienceList;

    private Double avgRating; // only for mentor
}
