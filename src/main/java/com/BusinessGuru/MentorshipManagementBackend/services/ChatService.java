package com.BusinessGuru.MentorshipManagementBackend.services;

import com.BusinessGuru.MentorshipManagementBackend.Dto.ChatDTO;
import com.BusinessGuru.MentorshipManagementBackend.entities.Chat;
import com.BusinessGuru.MentorshipManagementBackend.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;


    public Boolean saveChats(List<ChatDTO> chatList) {
        for(ChatDTO chatDTO : chatList){
            Chat chat = new Chat();
            chat.setFromId(chatDTO.getFromId());
            chat.setToId(chatDTO.getToId());
            chat.setMessage(chatDTO.getMessage());
            chatRepository.save(chat);
        }

        return true;

    }

    public List<Chat> getChats(String fromId, String toId) {
        List<Chat> chatList = chatRepository.findChatsBetweenUsers( fromId,  toId);
        return chatList;
    }
}
