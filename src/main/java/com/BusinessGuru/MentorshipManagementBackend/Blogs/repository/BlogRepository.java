package com.BusinessGuru.MentorshipManagementBackend.Blogs.repository;

import com.BusinessGuru.MentorshipManagementBackend.Blogs.entities.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Blog,String> {
}
