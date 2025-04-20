package com.BusinessGuru.MentorshipManagementBackend.entities;

import com.BusinessGuru.MentorshipManagementBackend.commons.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chats")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String fromId;

    private String toId;

    private String message;
}
