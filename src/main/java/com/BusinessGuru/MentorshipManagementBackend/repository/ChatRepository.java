package com.BusinessGuru.MentorshipManagementBackend.repository;

import com.BusinessGuru.MentorshipManagementBackend.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat,String> {

    @Query("SELECT c FROM Chat c " +
            "WHERE (c.fromId = :fromId AND c.toId = :toId) " +
            "   OR (c.fromId = :toId AND c.toId = :fromId) " +
            "ORDER BY c.createdAt ASC")
    List<Chat> findChatsBetweenUsers(@Param("fromId") String fromId, @Param("toId") String toId);

}
