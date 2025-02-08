package com.nightly.controller;

import com.nightly.service.ImageService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * Cloudinary 이미지 서버 테스트를 위한 컨트롤러
 *
 * 클라이언트로부터 MultipartFile을 요청 받아서 처리
 * ImageService에 MultipartFile을 전달하여 imageUrl을 반환
 *
 */
@RestController
@RequestMapping("/test/cloudinary")
public class CloudinaryController {

    private final ImageService imageService;

    public CloudinaryController(ImageService imageService) {
        this.imageService = imageService;
    }

    
    @PostMapping("/upload")
    public ResponseEntity<Map> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        Map uploadedImage = imageService.uploadImage(file);
        return ResponseEntity.ok().body(uploadedImage);
    }
    

    @GetMapping("/delete/{publicId}")
    public ResponseEntity<Map> deleteFile (@PathVariable("publicId") String publicId) throws IOException {
    	Map deletedImage = imageService.deleteImage(publicId);
    	return ResponseEntity.ok().body(deletedImage);
    }
}