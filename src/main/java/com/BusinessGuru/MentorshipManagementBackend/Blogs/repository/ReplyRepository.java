package com.BusinessGuru.MentorshipManagementBackend.Blogs.repository;

import com.BusinessGuru.MentorshipManagementBackend.Blogs.entities.ReplyToComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<ReplyToComment,String> {

    List<ReplyToComment> findByParentCommentId(String id);
}
