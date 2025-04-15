package com.BusinessGuru.MentorshipManagementBackend.Blogs.entities;

import com.BusinessGuru.MentorshipManagementBackend.commons.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "replies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyToComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String userId;

    private String replyComment;

    private String replyToUserId;

    private String parentCommentId;
}
