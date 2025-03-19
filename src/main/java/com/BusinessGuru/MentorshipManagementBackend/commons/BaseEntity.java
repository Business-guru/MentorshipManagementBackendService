package com.BusinessGuru.MentorshipManagementBackend.commons;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jdk.jfr.Timestamp;

import java.util.Date;

@MappedSuperclass
public class BaseEntity {
    private Date createdAt;

    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        Date now = new Date();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }
}
