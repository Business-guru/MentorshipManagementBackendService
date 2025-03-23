package com.BusinessGuru.MentorshipManagementBackend.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceReponse {
    private String id;

    private String orgName;
    private String position;
    private String description;

    private Date fromDate;

    private Date toDate;
}
