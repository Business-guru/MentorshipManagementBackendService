package com.BusinessGuru.MentorshipManagementBackend.Blogs.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogDto {
    private String title;

    private Set<String> category;

    private String content;

    private List<MultipartFile> images;

    private List<String> tags;

    private String userId;

}
