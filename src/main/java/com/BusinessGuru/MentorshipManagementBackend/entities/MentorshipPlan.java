package com.BusinessGuru.MentorshipManagementBackend.entities;

import com.BusinessGuru.MentorshipManagementBackend.commons.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "plans")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MentorshipPlan extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    private UserProfile createdBy;

    private String planName;

    private String Price;

    private String description;

    private List<String> features;

    private Integer totalSlots;

    private Integer currentSlots;

    private Integer duration;
}
