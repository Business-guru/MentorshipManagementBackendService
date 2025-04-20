package com.BusinessGuru.MentorshipManagementBackend.Blogs.repository;

import com.BusinessGuru.MentorshipManagementBackend.Blogs.entities.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog,String> , JpaSpecificationExecutor<Blog>{
    List<Blog> findByUserId(String userId);


    @Query(value = "SELECT * FROM blogs b JOIN blog_categories c ON b.id = c.blog_id WHERE c.categories IN (:categories)",
            countQuery = "SELECT count(*) FROM blogs b JOIN blog_categories c ON b.id = c.blog_id WHERE c.categories IN (:categories)",
            nativeQuery = true)
    Page<Blog> findByCategoriesIn(@Param("categories") List<String> categories, Pageable pageable);

}
