package com.BusinessGuru.MentorshipManagementBackend.services;

import com.BusinessGuru.MentorshipManagementBackend.Dto.ProfileResponse;
import com.BusinessGuru.MentorshipManagementBackend.Dto.UserSyncDto;
import com.BusinessGuru.MentorshipManagementBackend.Dto.ProfileRequest;
import com.BusinessGuru.MentorshipManagementBackend.entities.UserProfile;
import com.BusinessGuru.MentorshipManagementBackend.repository.UserProfileRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private UserProfileRepository profileRepository;

    @Autowired
    private ModelMapper modelMapper;


    public Boolean syncUserFromAuthentication(UserSyncDto syncDto) {
        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(syncDto.getUserId());
        userProfile.setEmail(syncDto.getEmail());
        userProfile.setUserType(syncDto.getUserType());
        userProfile.setIsProfileCompleted(false);
        userProfile.setFirstName(userProfile.getFirstName());
        userProfile.setLastName(userProfile.getLastName());
        profileRepository.save(userProfile);
        return true;
    }

    public ProfileResponse completeProfile(String userId, ProfileRequest request) {
        Optional<UserProfile> userProfileOptional = profileRepository.findByUserId(userId);
        if(userProfileOptional.isEmpty()){
            // todo : throw resource not found exception
        }
        UserProfile profile = userProfileOptional.get();
        profile.setAbout(request.getAbout());
        profile.setBio(request.getBio());
        profile.setSkills(request.getSkills());
        profile.setExperienceList(request.getExperienceList());
        profile.setIsProfileCompleted(true);

        UserProfile savedProfile = profileRepository.save(profile);
        ProfileResponse profileResponse = modelMapper.map(savedProfile,ProfileResponse.class);
        return profileResponse;

    }

    public ProfileResponse updateProfile(String userId, ProfileRequest request) {
        Optional<UserProfile> userProfileOptional = profileRepository.findByUserId(userId);
        if(userProfileOptional.isEmpty()){
            // todo : throw resource not found exception
        }
        UserProfile profile = userProfileOptional.get();
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
            profile.setExperienceList(request.getExperienceList());
        }

        UserProfile savedProfile = profileRepository.save(profile);
        ProfileResponse profileResponse = modelMapper.map(savedProfile,ProfileResponse.class);
        return profileResponse;

    }

    public Boolean getProfileCompletionStatus(String userId) {
        Optional<UserProfile> userProfileOptional = profileRepository.findByUserId(userId);
        if(userProfileOptional.isEmpty()){
            // todo : throw resource not found exception
        }
        return userProfileOptional.get().getIsProfileCompleted();
    }

    public ProfileResponse getProfile(String userId) {
        Optional<UserProfile> userProfileOptional = profileRepository.findByUserId(userId);
        if(userProfileOptional.isEmpty()){
            // todo : throw resource not found exception
        }
        UserProfile profile = userProfileOptional.get();
        ProfileResponse profileResponse = modelMapper.map(profile,ProfileResponse.class);
        return profileResponse;
    }
}
