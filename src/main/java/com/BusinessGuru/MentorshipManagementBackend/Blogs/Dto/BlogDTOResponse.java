package com.BusinessGuru.MentorshipManagementBackend.Blogs.Dto;

import com.BusinessGuru.MentorshipManagementBackend.Blogs.entities.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogDTOResponse {
    private String title;

    private Set<String> category;

    private String content;

    private List<String> tags;

    private String userId;

    private Date createdAt;

    private Date updatedAt;

    private Integer likes;
}
