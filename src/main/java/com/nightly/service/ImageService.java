package com.nightly.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ImageService {

    private final Cloudinary cloudinary;
    
    public ImageService(Cloudinary cloudinary) {
    	this.cloudinary = cloudinary;
    }

    /**
     * 이미지 업로드 처리 메서드
     * @param file : 이미지 저장 객체 MultipartFile
     * @return 저장된 이미지의 정보를 Key : Value 형태로 반환
     * JSON 응답 예시
        {
          "asset_folder": "",
          "signature": "50a0284f9ec6ff5c7368a3c6587800b635b84937",
          "format": "jpg",
          "resource_type": "image",
          "secure_url": "https://res.cloudinary.com/nightly/image/upload/v1738995002/hyiww4ncvejmthpi42er.jpg",
          "created_at": "2025-02-08T06:10:02Z",
          "asset_id": "2c0cb9aa056783cfaff7edd79ffc4f95",
          "version_id": "dae081979ebb1e91997d041dccd4b86c",
          "type": "upload",
          "display_name": "hyiww4ncvejmthpi42er",
          "version": 1738995002,
          "url": "http://res.cloudinary.com/nightly/image/upload/v1738995002/hyiww4ncvejmthpi42er.jpg",
          "public_id": "hyiww4ncvejmthpi42er",
          "tags": [],
          "original_filename": "file",
          "api_key": "668834994883695",
          "bytes": 104806,
          "width": 948,
          "etag": "9e96d4bdc8baf468f37b1e7104814e26",
          "placeholder": false,
          "height": 903
        }
     * @throws IOException
     */
    public Map uploadImage(MultipartFile file) throws IOException {
    	if (file.isEmpty()) {
    		throw new IllegalArgumentException();
    	}
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
//        return String.valueOf(uploadResult.get("secure_url")); // 업로드된 이미지 URL 반환
//        return String.valueOf(uploadResult.get("public_id")); // 업로드된 이미지 public ID 반환 -> 삭제 시 사용
        return uploadResult;
    }

    /**
     * 이미지 삭제 처리 메서드
     * @param publicId : 이미지 업로드(저장) 시 Cloudinary 서버에 저장된 이미지의 고유 ID -> 삭제 시 활용
     * @return : 삭제 성공 시 {"result":"ok"} 반환
     * @throws IOException
     */
    public Map<String, Object> deleteImage(String publicId) throws IOException {
    	return cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }
}