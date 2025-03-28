package com.BusinessGuru.MentorshipManagementBackend.Blogs.service;

import com.BusinessGuru.MentorshipManagementBackend.Blogs.Dto.BlogDTOResponse;
import com.BusinessGuru.MentorshipManagementBackend.Blogs.Dto.BlogDto;
import com.BusinessGuru.MentorshipManagementBackend.Blogs.entities.Blog;
import com.BusinessGuru.MentorshipManagementBackend.Blogs.repository.BlogRepository;
import com.BusinessGuru.MentorshipManagementBackend.repository.UserProfileRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BlogService {

    @Autowired
    private ImageUploadService imageUploadService;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserProfileRepository profileRepository;

    @Autowired
    private ModelMapper modelMapper;

    public BlogDTOResponse createBlog(BlogDto blogDTO) {
        if(profileRepository.findByUserId(blogDTO.getUserId()).isEmpty()){
            // todo throw ex
        }
        Blog blog = new Blog();
        blog.setUserId(blogDTO.getUserId());
        blog.setTitle(blogDTO.getTitle());
        blog.setContent(blogDTO.getContent());
        blog.setTags(blogDTO.getTags());
        blog.setCategory(blogDTO.getCategory());
        blog.setLikes(0);
        List<String> images = new ArrayList<>();
        if (blogDTO.getImages().size() > 2) {

            // todo : throw appropriate exception
//            throw new ApiException("only 2 images are allowed");
        }
        for (MultipartFile image : blogDTO.getImages()) {
            Map response = imageUploadService.upload(image, "blogApp");
            String url = (String) response.get("url");
            images.add(url);
        }
        blog.setImages(images);
        Blog savedBlog  = blogRepository.save(blog);
        return modelMapper.map(savedBlog,BlogDTOResponse.class);
    }

    public BlogDTOResponse getPostById(String postId) {
        Optional<Blog> blogOptional = blogRepository.findById(postId);
        if(blogOptional.isEmpty()){
            // throw post not found exception
        }
        Blog blog = blogOptional.get();
        return modelMapper.map(blog, BlogDTOResponse.class);
    }

    public Boolean deletePost(String postId, String userId){
        Optional<Blog> blogOptional = blogRepository.findById(postId);
        if(blogOptional.isEmpty()){
            // throw post not found exception
        }
        Blog blog = blogOptional.get();
        if(!blog.getUserId().equals(userId)){
            // throw unauthorized exception
        }
        blogRepository.delete(blog);
        return true;
    }

    public BlogDTOResponse likePost(String postId, Integer cnt) {
        Optional<Blog> blogOptional = blogRepository.findById(postId);
        if(blogOptional.isEmpty()){
            // throw post not found exception
        }
        Blog blog = blogOptional.get();
        Integer likes = blog.getLikes();
        likes = likes+cnt;
        blog.setLikes(likes);
        Blog savedBlog  = blogRepository.save(blog);
        return modelMapper.map(savedBlog,BlogDTOResponse.class);

    }
}
