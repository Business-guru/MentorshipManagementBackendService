package com.BusinessGuru.MentorshipManagementBackend.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MiniMentor {
    private String userId;
    private String name;
    private String bio;
    private List<String> skills;
    private Double avgRating;
}
