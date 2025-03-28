package com.BusinessGuru.MentorshipManagementBackend.Blogs.repository;

import com.BusinessGuru.MentorshipManagementBackend.Blogs.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment,String> {
}
