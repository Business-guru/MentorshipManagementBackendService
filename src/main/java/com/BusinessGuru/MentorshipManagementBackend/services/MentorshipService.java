package com.BusinessGuru.MentorshipManagementBackend.services;

import com.BusinessGuru.MentorshipManagementBackend.Dto.MiniMentor;
import com.BusinessGuru.MentorshipManagementBackend.Dto.PlanDto;
import com.BusinessGuru.MentorshipManagementBackend.entities.MentorshipPlan;
import com.BusinessGuru.MentorshipManagementBackend.entities.UserProfile;
import com.BusinessGuru.MentorshipManagementBackend.enums.UserType;
import com.BusinessGuru.MentorshipManagementBackend.repository.PlanRepository;
import com.BusinessGuru.MentorshipManagementBackend.repository.UserProfileRepository;
import jakarta.security.auth.message.AuthException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

//@Service
//public class MentorshipService {
//
//    @Autowired
//    private UserProfileRepository profileRepository;
//
//    public List<MiniMentor> getAllMentors() {
//        List<UserProfile> mentorList = profileRepository.findByUserType(UserType.MENTOR);
//        List<MiniMentor> miniMentorList = miniMentorMapper(mentorList);
//        return miniMentorList;
//    }
//
//    private List<MiniMentor> miniMentorMapper(List<UserProfile> userProfileList){
//        List<MiniMentor> miniMentorList = new ArrayList<>();
//        for(UserProfile profile : userProfileList){
//            MiniMentor miniMentor = new MiniMentor();
//            miniMentor.setName(profile.getFirstName()+" "+profile.getLastName());
//            miniMentor.setBio(profile.getBio());
//            miniMentor.setUserId(profile.getUserId());
//            miniMentor.setAvgRating(profile.getAvgRating());
//            miniMentor.setSkills(profile.getSkills());
//            miniMentorList.add(miniMentor);
//        }
//        return miniMentorList;
//
//    }
//}

@Service
public class MentorshipService {

    @Autowired
    private UserProfileRepository profileRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PlanRepository planRepository;

    public Page<MiniMentor> getAllMentors(List<String> skills, String sortBy, String order, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending());
        Page<UserProfile> userProfiles;

        if (skills != null && !skills.isEmpty()) {
            userProfiles = profileRepository.findByUserTypeAndSkillsIn(UserType.MENTOR, skills.stream().map(String :: toLowerCase).toList(), pageable);
        } else {
            userProfiles = profileRepository.findByUserType(UserType.MENTOR, pageable);
        }

        return userProfiles.map(this::convertToMiniMentor);
    }

//    public Page<MiniMentor> searchMentors(String query, int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<UserProfile> searchResults = profileRepository.searchMentors(query, UserType.MENTOR, pageable);
//        return searchResults.map(this::convertToMiniMentor);
//    }

    public Page<MiniMentor> searchMentors(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        // Convert multi-word query into multiple LIKE conditions
        String processedQuery = Arrays.stream(query.split("\\s+"))
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.joining("%")); // Makes "Prat1 Lohar1" → "Prat1%Lohar1"

        Page<UserProfile> searchResults = profileRepository.searchMentors(processedQuery, UserType.MENTOR, pageable);
        return searchResults.map(this::convertToMiniMentor);
    }


    private MiniMentor convertToMiniMentor(UserProfile profile) {
        return new MiniMentor(
                profile.getUserId(),
                profile.getFirstName() + " " + profile.getLastName(),
                profile.getBio(),
                profile.getSkills(),
                profile.getAvgRating()
        );
    }

    public PlanDto createPlan(String userId, PlanDto planDto) {

        Optional<UserProfile> profile = profileRepository.findByUserId(userId);

        if(profile.isEmpty()){
            // throw not found exception
        }
        if(profile.get().getUserType().equals(UserType.USER)){
            // throw unauthorized error
        }

        MentorshipPlan plan = modelMapper.map(planDto, MentorshipPlan.class);
        plan.setCreatedBy(profile.get());

        MentorshipPlan savedPlan = planRepository.save(plan);

        return modelMapper.map(savedPlan,PlanDto.class);

    }

    public List<PlanDto> getAllPlansOfMentor(String userId){
        Optional<UserProfile>userProfileOptional = profileRepository.findByUserId(userId);
        List<MentorshipPlan> planList = planRepository.findByCreatedBy(userProfileOptional.get());

        Type listType = new TypeToken<List<PlanDto>>() {}.getType();
        return modelMapper.map(planList, listType);
    }
}
