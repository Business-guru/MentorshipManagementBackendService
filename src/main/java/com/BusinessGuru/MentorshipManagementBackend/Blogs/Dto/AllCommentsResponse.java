package com.BusinessGuru.MentorshipManagementBackend.Blogs.Dto;

import com.BusinessGuru.MentorshipManagementBackend.Blogs.entities.Comment;
import com.BusinessGuru.MentorshipManagementBackend.Blogs.entities.ReplyToComment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllCommentsResponse {
    private Comment comment;

    private List<ReplyToComment> replyToCommentList;
}
