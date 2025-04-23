package com.BusinessGuru.MentorshipManagementBackend.controller;

import com.BusinessGuru.MentorshipManagementBackend.Dto.ProfileRequest;
import com.BusinessGuru.MentorshipManagementBackend.Dto.ProfileResponse;
import com.BusinessGuru.MentorshipManagementBackend.Dto.UserSyncDto;
import com.BusinessGuru.MentorshipManagementBackend.commons.ApiResponse;
import com.BusinessGuru.MentorshipManagementBackend.commons.Meta;
import com.BusinessGuru.MentorshipManagementBackend.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/mentorship/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("/sync-user")
    ResponseEntity<ApiResponse<Boolean>> syncUserFromAuthentication(@RequestBody UserSyncDto syncDto){
        // todo : this is temporary to be replaced by kafka consumer
        Boolean syncResponse = profileService.syncUserFromAuthentication(syncDto);
        ApiResponse<Boolean> response = new ApiResponse<>(new Meta("sync success",true),syncResponse);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/complete-profile")
    ResponseEntity<ApiResponse<ProfileResponse>> completeProfile(@RequestHeader(name = "x-user-id") String userId, @RequestBody ProfileRequest request){
        ProfileResponse profileResponse = profileService.completeProfile(userId,request);
        ApiResponse<ProfileResponse> response = new ApiResponse<>(new Meta("profile completed successfully",true),profileResponse);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-profile")
    ResponseEntity<ApiResponse<ProfileResponse>> updateProfile(@RequestHeader(name = "x-user-id") String userId, @RequestBody ProfileRequest request){
        ProfileResponse profileResponse = profileService.updateProfile(userId,request);
        ApiResponse<ProfileResponse> response = new ApiResponse<>(new Meta("profile updated successfully",true),profileResponse);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/isProfileComplete")
    ResponseEntity<ApiResponse<Boolean>> getProfileCompletionStatus(@RequestHeader(name = "x-user-id") String userId){
        Boolean profileCompletionStatus = profileService.getProfileCompletionStatus(userId);
        ApiResponse<Boolean> response = new ApiResponse<>(new Meta("status fetch success",true),profileCompletionStatus);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-profile")
    ResponseEntity<ApiResponse<ProfileResponse>> getProfile(@RequestHeader(name = "x-user-id") String userId){
        ProfileResponse profileResponse = profileService.getProfile(userId);
        ApiResponse<ProfileResponse> response = new ApiResponse<>(new Meta("profile fetched successfully",true),profileResponse);
        return ResponseEntity.ok(response);
    }
}
