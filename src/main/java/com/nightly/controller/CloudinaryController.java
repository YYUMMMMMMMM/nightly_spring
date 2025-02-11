package com.nightly.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nightly.service.ImageService;

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
    
    @PostMapping
    public ResponseEntity<Map> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        Map uploadedImage = imageService.uploadImage(file);
        return ResponseEntity.ok().body(uploadedImage);
    }

    @DeleteMapping("/{publicId}")
    public ResponseEntity<Map> deleteFile (@PathVariable("publicId") String publicId) throws IOException {
    	Map deletedImage = imageService.deleteImage(publicId);
    	return ResponseEntity.ok().body(deletedImage);
    }
}