package com.BusinessGuru.MentorshipManagementBackend.Blogs.service;

import com.BusinessGuru.MentorshipManagementBackend.Blogs.Dto.AllCommentsResponse;
import com.BusinessGuru.MentorshipManagementBackend.Blogs.Dto.CommentDTO;
import com.BusinessGuru.MentorshipManagementBackend.Blogs.Dto.ReplyDTO;
import com.BusinessGuru.MentorshipManagementBackend.Blogs.entities.Blog;
import com.BusinessGuru.MentorshipManagementBackend.Blogs.entities.Comment;
import com.BusinessGuru.MentorshipManagementBackend.Blogs.entities.ReplyToComment;
import com.BusinessGuru.MentorshipManagementBackend.Blogs.repository.BlogRepository;
import com.BusinessGuru.MentorshipManagementBackend.Blogs.repository.CommentRepository;
import com.BusinessGuru.MentorshipManagementBackend.Blogs.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private ReplyRepository replyRepository;


    public CommentDTO addComment(String postId, String userId, CommentDTO commentDTO) {
        Optional<Blog> blogOptional = blogRepository.findById(postId);
        if(blogOptional.isEmpty()){
            // throw exception
        }
        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setComment(commentDTO.getComment());
        comment.setBlog(blogOptional.get());

        Comment savedComment = commentRepository.save(comment);

        return new CommentDTO(savedComment.getComment());

    }

    public CommentDTO replyToComment(String postId, String userId, String parentCommentId, ReplyDTO replyDTO) {
        // Check if the blog post exists
        Optional<Blog> blogOptional = blogRepository.findById(postId);
        if (blogOptional.isEmpty()) {
            // throw new RuntimeException("Blog post not found");
        }

        // Find the parent comment that the user is replying to
        Optional<Comment> parentCommentOptional = commentRepository.findById(parentCommentId);
        if (parentCommentOptional.isEmpty()) {
            // throw new RuntimeException("Parent comment not found");
        }

        ReplyToComment replyComment = new ReplyToComment();
        replyComment.setUserId(userId);
        replyComment.setReplyComment(replyDTO.getComment());
        replyComment.setParentCommentId(parentCommentId);  // Set the parent comment
        replyComment.setReplyToUserId(replyDTO.getReplyToUserId());

        replyComment = replyRepository.save(replyComment);

        return new CommentDTO(replyComment.getReplyComment());
    }

    public List<AllCommentsResponse> getCommentsWithReplies(String postId) {
        // Fetch all comments related to the post
        Optional<Blog> blogOptional = blogRepository.findById(postId);
        if (blogOptional.isEmpty()) {
            throw new RuntimeException("Blog post not found");
        }

        // Get all parent comments for the blog post
        List<Comment> parentComments = commentRepository.findAll();
        List<AllCommentsResponse> allCommentsResponses = new ArrayList<>();
        for(Comment comment : parentComments){
            List<ReplyToComment> replyToCommentList = replyRepository.findByParentCommentId(comment.getId());
            AllCommentsResponse allCommentsResponse = new AllCommentsResponse(comment,replyToCommentList);
            allCommentsResponses.add(allCommentsResponse);
        }
        return allCommentsResponses;

    }
}
