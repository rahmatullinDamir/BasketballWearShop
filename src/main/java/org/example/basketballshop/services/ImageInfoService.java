package org.example.basketballshop.services;

import jakarta.servlet.http.HttpServletResponse;
import org.example.basketballshop.models.ImageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageInfoService {
    ImageInfo saveImage(MultipartFile uploadImage);
    List<ImageInfo> saveImages(MultipartFile[] uploadImages);
}