package com.BusinessGuru.MentorshipManagementBackend.Blogs.entities;

import com.BusinessGuru.MentorshipManagementBackend.commons.BaseEntity;
import com.BusinessGuru.MentorshipManagementBackend.entities.UserProfile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "blogs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @NonNull
    private String title;

    @ElementCollection
    private List<String> categories;

    private String content;

    @ElementCollection
    private List<String> images;

    @ElementCollection
    private List<String> tags;

    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Comment> comments;

    private String userId;

    private Integer likes;
}
