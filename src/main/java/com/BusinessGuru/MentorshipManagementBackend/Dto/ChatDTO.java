package com.BusinessGuru.MentorshipManagementBackend.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatDTO {

    private String fromId;

    private String toId;

    private String message;
}
