package com.nightly.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    // 이미지 업로드
    Map uploadImage(MultipartFile file) throws IOException;
    
    // 이미지 삭제
    Map deleteImage(String publicId) throws IOException;
    
    // Cloudinary URL -> Public ID 추출
    String getPublicIdFromCloudinaryUrl(String url);
}
