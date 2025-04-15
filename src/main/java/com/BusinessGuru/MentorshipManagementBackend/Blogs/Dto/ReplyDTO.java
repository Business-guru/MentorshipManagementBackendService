package com.BusinessGuru.MentorshipManagementBackend.Blogs.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {
    private String comment;
    private String replyToUserId;
}
