package com.anil.erp.service;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadCloudinaryService {
	
	private final Cloudinary cloudinary;

    public UploadCloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public void uploadFile(MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                        "ncrt/9/math/ch1", "student_uploads",
                        "resource_type", "auto"
                    )
            );

            System.out.println(uploadResult.get("secure_url").toString()); ;

        } catch (Exception e) {
        	e.printStackTrace();
//            throw new RuntimeException("Image upload failed", e);
        }
    }

}
