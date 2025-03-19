package com.BusinessGuru.MentorshipManagementBackend.Dto;

import com.BusinessGuru.MentorshipManagementBackend.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSyncDto {
    private String userId;
    private String email;
    private String firstName;
    private String lastName;
    private UserType userType;
}
