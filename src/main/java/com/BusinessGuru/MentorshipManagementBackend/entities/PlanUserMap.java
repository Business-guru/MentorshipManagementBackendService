package com.BusinessGuru.MentorshipManagementBackend.entities;

import com.BusinessGuru.MentorshipManagementBackend.enums.PlanStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "planusermap")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanUserMap {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String planId;

    private String userId;

    @Enumerated(EnumType.STRING)
    private PlanStatus status;

    private Long datePurchased;
}
