package com.BusinessGuru.MentorshipManagementBackend.Blogs.Dto;

import com.BusinessGuru.MentorshipManagementBackend.Blogs.entities.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private String comment;

    private List<CommentDTO> replies;

    public CommentDTO(String comment) {
        this.comment = comment;
    }
}