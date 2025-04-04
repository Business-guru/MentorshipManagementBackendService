package com.BusinessGuru.MentorshipManagementBackend.Blogs.service;

import com.BusinessGuru.MentorshipManagementBackend.Blogs.Dto.CommentDTO;
import com.BusinessGuru.MentorshipManagementBackend.Blogs.entities.Blog;
import com.BusinessGuru.MentorshipManagementBackend.Blogs.entities.Comment;
import com.BusinessGuru.MentorshipManagementBackend.Blogs.repository.BlogRepository;
import com.BusinessGuru.MentorshipManagementBackend.Blogs.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public CommentDTO replyToComment(String postId, String userId, String parentCommentId, CommentDTO commentDTO) {
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

        Comment replyComment = new Comment();
        replyComment.setUserId(userId);
        replyComment.setComment(commentDTO.getComment());
        replyComment.setBlog(blogOptional.get());
        replyComment.setParentComment(parentCommentOptional.get());  // Set the parent comment

        replyComment = commentRepository.save(replyComment);

        return new CommentDTO(replyComment.getComment());
    }

    public List<CommentDTO> getCommentsWithReplies(String postId) {
        // Fetch all comments related to the post
        Optional<Blog> blogOptional = blogRepository.findById(postId);
        if (blogOptional.isEmpty()) {
            throw new RuntimeException("Blog post not found");
        }

        // Get all parent comments for the blog post
        List<Comment> parentComments = commentRepository.findByBlogAndParentCommentIsNull(blogOptional.get());

        // Prepare the response by converting each comment to CommentDTO and adding its replies
        List<CommentDTO> commentDTOs = new ArrayList<>();
        for (Comment parentComment : parentComments) {
            CommentDTO parentDTO = new CommentDTO(parentComment.getComment());

            // Get all replies for the parent comment
            List<Comment> replies = commentRepository.findByParentComment(parentComment);
            List<CommentDTO> replyDTOs = new ArrayList<>();
            for (Comment reply : replies) {
                replyDTOs.add(new CommentDTO(reply.getComment()));
            }

            // Add replies to the parent comment DTO
            parentDTO.setReplies(replyDTOs);
            commentDTOs.add(parentDTO);
        }

        return commentDTOs;
    }
}
