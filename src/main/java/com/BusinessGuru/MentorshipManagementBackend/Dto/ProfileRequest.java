package com.BusinessGuru.MentorshipManagementBackend.Dto;

import com.BusinessGuru.MentorshipManagementBackend.entities.Experience;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileRequest {

    private Set<String> skills;

    private String about;

    private String bio;

    private List<Experience> experienceList;

}
