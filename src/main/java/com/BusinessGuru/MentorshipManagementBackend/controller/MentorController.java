package com.BusinessGuru.MentorshipManagementBackend.controller;

import com.BusinessGuru.MentorshipManagementBackend.Dto.MiniMentor;
import com.BusinessGuru.MentorshipManagementBackend.Dto.PlanDto;
import com.BusinessGuru.MentorshipManagementBackend.commons.ApiResponse;
import com.BusinessGuru.MentorshipManagementBackend.commons.Meta;
import com.BusinessGuru.MentorshipManagementBackend.entities.MentorshipPlan;
import com.BusinessGuru.MentorshipManagementBackend.services.MentorshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/mentor")
public class MentorController {

    @Autowired
    private MentorshipService mentorshipService;

    @GetMapping("/getAllMentors")
    ResponseEntity<ApiResponse<Page<MiniMentor>>> getAllUsers(
            @RequestParam(required = false) List<String> skills,
            @RequestParam(defaultValue = "avgRating") String sortBy,
            @RequestParam(defaultValue = "desc") String order,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<MiniMentor> userList = mentorshipService.getAllMentors(skills, sortBy, order, page, size);
        ApiResponse<Page<MiniMentor>> response = new ApiResponse<>(new Meta("Mentor list fetched successfully", true), userList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/searchMentors")
    ResponseEntity<ApiResponse<Page<MiniMentor>>> searchMentors(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<MiniMentor> searchResults = mentorshipService.searchMentors(query, page, size);
        ApiResponse<Page<MiniMentor>> response = new ApiResponse<>(new Meta("Search results fetched successfully", true), searchResults);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/createMentorshipPlan")
    ResponseEntity<ApiResponse<PlanDto>> createPlan(@RequestHeader(name = "x-user-id") String userId, @RequestBody PlanDto planDto){
        PlanDto plan = mentorshipService.createPlan(userId,planDto);
        ApiResponse<PlanDto> response = new ApiResponse<>(new Meta("plan created successfully",true),plan);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllPlansOfMentor")
    ResponseEntity<ApiResponse<List<PlanDto>>> getAllPlans(@RequestHeader(name = "x-user-id")String userId){
        List<PlanDto> mentorshipPlanList = mentorshipService.getAllPlansOfMentor(userId);
        ApiResponse< List<PlanDto>> response = new ApiResponse<>(new Meta("plans fetched successfully",true),mentorshipPlanList);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/subscribePlan/{planId}")
    ResponseEntity<ApiResponse<PlanDto>> subscribePlan(@PathVariable(name = "planId") String planId, @RequestHeader(name = "x-user-id")String userId){
        PlanDto planDto = mentorshipService.subscribePlan(planId,userId);
        ApiResponse<PlanDto> response = new ApiResponse<>(new Meta("subscription added successfully",true),planDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllSubscribedPlans")
    ResponseEntity<ApiResponse<List<PlanDto>>> getAllSubscribedPlans( @RequestHeader(name = "x-user-id")String userId){
        List<PlanDto> planDtoList = mentorshipService.getAllSubscribedPlans(userId);
        ApiResponse<List<PlanDto>> response = new ApiResponse<>(new Meta("subscription added successfully",true),planDtoList);
        return ResponseEntity.ok(response);
    }

}
