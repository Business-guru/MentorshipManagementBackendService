package com.BusinessGuru.MentorshipManagementBackend.Blogs.service;

import com.BusinessGuru.MentorshipManagementBackend.Blogs.Dto.BlogDTOResponse;
import com.BusinessGuru.MentorshipManagementBackend.Blogs.Dto.BlogDto;
import com.BusinessGuru.MentorshipManagementBackend.Blogs.entities.Blog;
import com.BusinessGuru.MentorshipManagementBackend.Blogs.repository.BlogRepository;
import com.BusinessGuru.MentorshipManagementBackend.repository.UserProfileRepository;
import jakarta.persistence.criteria.Predicate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
        blog.setCategories(blogDTO.getCategories());
        blog.setLikes(0);
        List<String> images = new ArrayList<>();
        if (blogDTO.getImages()!=null && blogDTO.getImages().size() > 2) {

            // todo : throw appropriate exception
//            throw new ApiException("only 2 images are allowed");
        }
        if(blogDTO.getImages()!=null){
            for (MultipartFile image : blogDTO.getImages()) {
                Map response = imageUploadService.upload(image, "blogApp");
                String url = (String) response.get("url");
                images.add(url);
            }
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

    public List<BlogDTOResponse> getAllBlogsOfUser(String userId) {
        List<Blog> blogList = blogRepository.findByUserId(userId);
        if(blogList.isEmpty()){
            // throw exception
        }
        List<BlogDTOResponse> blogDTOResponseList = new ArrayList<>();
        for(Blog blog : blogList){
            blogDTOResponseList.add(modelMapper.map(blog, BlogDTOResponse.class));
        }
        return blogDTOResponseList;
    }

    public Page<BlogDTOResponse> getAllBlogs(List<String> categories,
                                             String sortBy, String direction, int page, int size) {

        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Blog> blogPage = null;

        if (categories != null && !categories.isEmpty()) {
            blogPage = getAllBlogsWithFilters(categories, pageable);
        }else {
            blogPage = blogRepository.findAll(pageable);
        }

        return blogPage.map(this::convertToDto);
    }


    private BlogDTOResponse convertToDto(Blog blog) {
        return new BlogDTOResponse(
                blog.getId(),
                blog.getTitle(),
                blog.getCategories(),
                blog.getContent(),
                blog.getTags(),
                blog.getUserId(),
                blog.getCreatedAt(),
                blog.getUpdatedAt(),
                blog.getLikes(),
                blog.getImages()
        );
    }

    public Page<Blog> getAllBlogsWithFilters(List<String> categories, Pageable pageable) {
        return blogRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filter by categories (ElementCollection)
            if (categories != null && !categories.isEmpty()) {
                for (String category : categories) {
                    predicates.add(cb.isMember(category, root.get("categories")));
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        }, pageable);
    }


}
