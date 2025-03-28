package com.BusinessGuru.MentorshipManagementBackend.Blogs.controller;

import com.BusinessGuru.MentorshipManagementBackend.Blogs.Dto.BlogDTOResponse;
import com.BusinessGuru.MentorshipManagementBackend.Blogs.Dto.BlogDto;
import com.BusinessGuru.MentorshipManagementBackend.Blogs.service.BlogService;
import com.BusinessGuru.MentorshipManagementBackend.commons.ApiResponse;
import com.BusinessGuru.MentorshipManagementBackend.commons.Meta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/blogs")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @PostMapping("/create-post")
    public ResponseEntity<ApiResponse<BlogDTOResponse>> createBlog(@RequestHeader(name = "x-user-id") String userId,
                                                                   @RequestParam("title") String title,
                                                                   @RequestParam("content") String content,
                                                                   @RequestParam("tags") List<String> tags,
                                                                   @RequestParam("category") Set<String> category,
                                                                   @RequestParam("images") List<MultipartFile> images){
        BlogDto blogDto = new BlogDto(title,category,content,images,tags,userId);
        BlogDTOResponse savedRes = blogService.createBlog(blogDto);
        ApiResponse<BlogDTOResponse> response = new ApiResponse<>(new Meta("post created successfully",true),savedRes);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-post/{postId}")
    public ResponseEntity<ApiResponse<BlogDTOResponse>> getPost(@PathVariable(name = "postId") String postId){
        BlogDTOResponse getRes = blogService.getPostById(postId);
        ApiResponse<BlogDTOResponse> response = new ApiResponse<>(new Meta("post fetched successfully", true),getRes);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete-post/{postId}")
    public ResponseEntity<ApiResponse<Boolean>> deletePost(@RequestHeader(name = "x-user-id")String userId, @PathVariable(name = "postId")String postId){
        Boolean deleteRes = blogService.deletePost(postId,userId);
        ApiResponse<Boolean> response = new ApiResponse<>(new Meta("blog deleted successfully", true),deleteRes);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/like-post/{postId}")
    public ResponseEntity<ApiResponse<BlogDTOResponse>> likePost(@PathVariable(name = "postId") String postId,
                                                                 @RequestParam(required = false) Boolean unlike){
        Integer cnt = 1;
        if(unlike != null && unlike.equals(Boolean.TRUE)){
            cnt = -1;
        }
        BlogDTOResponse likeRes = blogService.likePost(postId,cnt);
        ApiResponse<BlogDTOResponse> response = new ApiResponse<>(new Meta("post liked successfully", true),likeRes);
        return ResponseEntity.ok(response);
    }



}
