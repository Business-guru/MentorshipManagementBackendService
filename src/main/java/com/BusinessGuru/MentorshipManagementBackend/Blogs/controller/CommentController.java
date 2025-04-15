package com.BusinessGuru.MentorshipManagementBackend.Blogs.controller;

import com.BusinessGuru.MentorshipManagementBackend.Blogs.Dto.AllCommentsResponse;
import com.BusinessGuru.MentorshipManagementBackend.Blogs.Dto.CommentDTO;
import com.BusinessGuru.MentorshipManagementBackend.Blogs.Dto.ReplyDTO;
import com.BusinessGuru.MentorshipManagementBackend.Blogs.entities.Comment;
import com.BusinessGuru.MentorshipManagementBackend.Blogs.service.CommentService;
import com.BusinessGuru.MentorshipManagementBackend.commons.ApiResponse;
import com.BusinessGuru.MentorshipManagementBackend.commons.Meta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("add-comment/{postId}")
    public ResponseEntity<ApiResponse<CommentDTO>> addComment(@PathVariable(name = "postId") String postId,@RequestHeader(name = "x-user-id") String userId, @RequestBody CommentDTO commentDTO){
        CommentDTO commentRes = commentService.addComment(postId, userId,commentDTO);
        ApiResponse<CommentDTO> response = new ApiResponse<>(new Meta("comment added succesfully",true),commentRes);
        return ResponseEntity.ok(response);
    }

    @PostMapping("reply-to-comment/{postId}/{parentCommentId}")
    public ResponseEntity<ApiResponse<CommentDTO>> replyToComment(@PathVariable(name = "postId") String postId,
                                                                  @PathVariable(name = "parentCommentId") String parentCommentId,
                                                                  @RequestHeader(name = "x-user-id") String userId,
                                                                  @RequestBody ReplyDTO replyDTO) {
        CommentDTO commentRes = commentService.replyToComment(postId, userId, parentCommentId, replyDTO);
        ApiResponse<CommentDTO> response = new ApiResponse<>(new Meta("Reply added successfully", true), commentRes);
        return ResponseEntity.ok(response);
    }

    @GetMapping("comments-with-replies/{postId}")
    public ResponseEntity<ApiResponse<List<AllCommentsResponse>>> getCommentsWithReplies(@PathVariable(name = "postId") String postId) {
        List<AllCommentsResponse> commentsRes = commentService.getCommentsWithReplies(postId);
        ApiResponse<List<AllCommentsResponse>> response = new ApiResponse<>(new Meta("Comments and replies fetched successfully", true), commentsRes);
        return ResponseEntity.ok(response);
    }


}
