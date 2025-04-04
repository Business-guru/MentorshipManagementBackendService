package com.BusinessGuru.MentorshipManagementBackend.Blogs.repository;

import com.BusinessGuru.MentorshipManagementBackend.Blogs.entities.Blog;
import com.BusinessGuru.MentorshipManagementBackend.Blogs.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,String> {
    // Find all comments for a specific blog post where the comment is a parent (no parentComment)
    List<Comment> findByBlogAndParentCommentIsNull(Blog blog);

    // Find all replies for a specific parent comment
    List<Comment> findByParentComment(Comment parentComment);
}
