package com.BusinessGuru.MentorshipManagementBackend.Blogs.service;

import com.BusinessGuru.MentorshipManagementBackend.commons.exceptions.ApiException;
import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class ImageUploadService {

    @Autowired
    private Cloudinary cloudinary;

    public Map upload(MultipartFile multipartFile, String folder) {
        try {
            Map<String, Object> uploadOptions = Map.of(
                    "folder", folder
            );
            Map data = this.cloudinary.uploader().upload(multipartFile.getBytes(), uploadOptions);
            return data;
        } catch (IOException e) {
            throw new ApiException("Image upload failed: " + e.getMessage());
        }
    }
}