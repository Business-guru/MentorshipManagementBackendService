//package com.BusinessGuru.MentorshipManagementBackend.config;
//
//import com.BusinessGuru.MentorshipManagementBackend.Dto.UserSyncDto;
//import com.BusinessGuru.MentorshipManagementBackend.services.ProfileService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class UserSyncListener {
//
//    @Autowired
//    private ProfileService profileService;
//
//    @KafkaListener(topics = "userprofile", groupId = "group-1", containerFactory = "userKafkaListenerContainerFactory")
//    public void syncUserFromAuth(UserSyncDto syncDto) {
//        System.out.println("Received user sync data: " + syncDto);
//        profileService.syncUserFromAuthentication(syncDto);
//    }
//}
