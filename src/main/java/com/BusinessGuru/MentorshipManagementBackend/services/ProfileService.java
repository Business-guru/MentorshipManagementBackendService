package com.BusinessGuru.MentorshipManagementBackend.services;

import com.BusinessGuru.MentorshipManagementBackend.Dto.ExperienceReponse;
import com.BusinessGuru.MentorshipManagementBackend.Dto.ProfileResponse;
import com.BusinessGuru.MentorshipManagementBackend.Dto.UserSyncDto;
import com.BusinessGuru.MentorshipManagementBackend.Dto.ProfileRequest;
import com.BusinessGuru.MentorshipManagementBackend.commons.exceptions.ResourceNotFoundException;
import com.BusinessGuru.MentorshipManagementBackend.entities.Experience;
import com.BusinessGuru.MentorshipManagementBackend.entities.UserProfile;
import com.BusinessGuru.MentorshipManagementBackend.enums.UserType;
import com.BusinessGuru.MentorshipManagementBackend.repository.ExperienceRepository;
import com.BusinessGuru.MentorshipManagementBackend.repository.UserProfileRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    @Autowired
    private UserProfileRepository profileRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ExperienceRepository experienceRepository;


    public Boolean syncUserFromAuthentication(UserSyncDto syncDto) {
        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(syncDto.getUserId());
        userProfile.setEmail(syncDto.getEmail());
        if(syncDto.getUserType().equals(UserType.MENTOR)){
            userProfile.setUserType(UserType.MENTOR);
        }else{
            userProfile.setUserType(UserType.USER);
        }
        userProfile.setIsProfileCompleted(false);
        profileRepository.save(userProfile);
        return true;
    }

    private void saveExperience(UserProfile profile, List<Experience> experienceList){
        for(Experience experience : experienceList){
            experience.setUserProfile(profile);
            experienceRepository.save(experience);
        }



    }

    public ProfileResponse completeProfile(String userId, ProfileRequest request) {

        Optional<UserProfile> userProfileOptional = profileRepository.findByUserId(userId);
        if(userProfileOptional.isEmpty()){
            throw new ResourceNotFoundException("profile","userId", userId);
            // todo : throw resource not found exception
        }
        UserProfile profile = userProfileOptional.get();
        saveExperience(profile,request.getExperienceList());
//        List<Experience> experienceList = experienceRepository.findAllById(ids);
        profile.setAbout(request.getAbout());
        profile.setBio(request.getBio());
        profile.setSkills(request.getSkills());
        profile.setFirstName(request.getFirstName());
        profile.setLastName(request.getLastName());
//        profile.setExperienceList(experienceList);

        profile.setIsProfileCompleted(true);

        UserProfile savedProfile = profileRepository.save(profile);

        ProfileResponse profileResponse = modelMapper.map(savedProfile,ProfileResponse.class);
        profileResponse.setExperienceList( experienceRepository.findByUserProfile(profile).stream().map(experience -> modelMapper.map(experience, ExperienceReponse.class)).toList());
        return profileResponse;

    }

    public ProfileResponse updateProfile(String userId, ProfileRequest request) {
        Optional<UserProfile> userProfileOptional = profileRepository.findByUserId(userId);
        if(userProfileOptional.isEmpty()){
            throw new ResourceNotFoundException("profile","userId", userId);
            // todo : throw resource not found exception
        }
        UserProfile profile = userProfileOptional.get();
        if(profile.getFirstName()!=null){
            profile.setFirstName(request.getFirstName());
        }
        if(profile.getLastName()!=null){
            profile.setLastName(request.getLastName());
        }
        if (request.getAbout() != null) {
            profile.setAbout(request.getAbout());
        }
        if (request.getBio() != null) {
            profile.setBio(request.getBio());
        }
        if (request.getSkills() != null) {
            profile.setSkills(request.getSkills());
        }
        if (request.getExperienceList() != null) {
            List<Experience> oldExperience = profile.getExperienceList();
            if(!oldExperience.isEmpty()){
                experienceRepository.deleteAll(oldExperience);
            }
            saveExperience(profile,request.getExperienceList());
        }

        UserProfile savedProfile = profileRepository.save(profile);
        ProfileResponse profileResponse = modelMapper.map(savedProfile,ProfileResponse.class);
        return profileResponse;

    }

    public Boolean getProfileCompletionStatus(String userId) {
        Optional<UserProfile> userProfileOptional = profileRepository.findByUserId(userId);
        if(userProfileOptional.isEmpty()){
            throw new ResourceNotFoundException("profile","userId", userId);

            // todo : throw resource not found exception
        }
        return userProfileOptional.get().getIsProfileCompleted();
    }

    public ProfileResponse getProfile(String userId) {
        Optional<UserProfile> userProfileOptional = profileRepository.findByUserId(userId);
        if(userProfileOptional.isEmpty()){
            throw new ResourceNotFoundException("profile","userId", userId);
            // todo : throw resource not found exception
        }
        UserProfile profile = userProfileOptional.get();
        ProfileResponse profileResponse = modelMapper.map(profile,ProfileResponse.class);
        return profileResponse;
    }
}
