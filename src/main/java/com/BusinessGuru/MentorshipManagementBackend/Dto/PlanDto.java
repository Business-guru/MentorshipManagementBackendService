package com.BusinessGuru.MentorshipManagementBackend.Dto;

import com.BusinessGuru.MentorshipManagementBackend.enums.PlanStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanDto {

    private String id;

    private String planName;

    private String Price;

    private String description;

    private List<String> features;

    private Integer totalSlots;

    private Integer currentSlots;

    private Integer duration;

    private PlanStatus planStatus;
}
