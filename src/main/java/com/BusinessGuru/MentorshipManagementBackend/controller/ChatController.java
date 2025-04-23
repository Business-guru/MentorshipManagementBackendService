package com.BusinessGuru.MentorshipManagementBackend.controller;

import com.BusinessGuru.MentorshipManagementBackend.Dto.ChatDTO;
import com.BusinessGuru.MentorshipManagementBackend.commons.ApiResponse;
import com.BusinessGuru.MentorshipManagementBackend.commons.Meta;
import com.BusinessGuru.MentorshipManagementBackend.entities.Chat;
import com.BusinessGuru.MentorshipManagementBackend.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mentorship/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/saveChats")
    ResponseEntity<ApiResponse<Boolean>> saveChats(@RequestBody List<ChatDTO> chatList){
        Boolean chatResponse = chatService.saveChats(chatList);
        ApiResponse<Boolean> response = new ApiResponse<>(new Meta("chats saved",true),true);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getChats/{fromId}/{toId}")
    ResponseEntity<ApiResponse<List<Chat>>> getChats(@PathVariable(name = "fromId") String fromId,
                                                     @PathVariable(name = "toId") String toId){
        List<Chat> chats = chatService.getChats(fromId,toId);
        ApiResponse<List<Chat>> response = new ApiResponse<>(new Meta("chats fetched",true),chats);
        return ResponseEntity.ok(response);
    }
}
