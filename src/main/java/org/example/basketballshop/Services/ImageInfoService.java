package org.example.basketballshop.Services;

import jakarta.servlet.http.HttpServletResponse;
import org.example.basketballshop.Models.ImageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageInfoService {
    ImageInfo saveImage(MultipartFile uploadImage);
    List<ImageInfo> saveImages(MultipartFile[] uploadImages);

    void writeImageToResponse(String imageName, HttpServletResponse response);
}