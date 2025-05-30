package org.example.basketballshop.Services.Impl;



import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.example.basketballshop.Models.ImageInfo;
import org.example.basketballshop.Repositories.ImageInfoRepository;
import org.example.basketballshop.Services.ImageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ImageInfoServiceImpl implements ImageInfoService {

    @Value("${file.url}")
    private String fileUrl;

    @Autowired
    private ImageInfoRepository imageInfoRepository;

    @Override
    public ImageInfo saveImage(MultipartFile uploadFile) {
        String imageStorageName = UUID.randomUUID() + "_" + uploadFile.getOriginalFilename();

        ImageInfo image = ImageInfo.builder()
                .contentType(uploadFile.getContentType())
                .size(uploadFile.getSize())
                .originalName(uploadFile.getOriginalFilename())
                .storageName(imageStorageName)
                .url(fileUrl + "/" + imageStorageName)
                .build();

        try {
            Files.copy(uploadFile.getInputStream(), Paths.get(fileUrl, imageStorageName));

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        imageInfoRepository.save(image);

        return image;
    }

    @Override
    public List<ImageInfo> saveImages(MultipartFile[] uploadFiles) {
        List<ImageInfo> imageInfoList = new ArrayList<>();

        for (MultipartFile file : uploadFiles) {
            if (!file.isEmpty()) {
                ImageInfo image = saveImage(file);
                imageInfoList.add(image);
            }
        }
        return imageInfoList;
    }



    @Override
    public void writeImageToResponse(String fileName, HttpServletResponse response) {
        ImageInfo file = imageInfoRepository.findByStorageName(fileName);
        if (file == null) {
            try {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;

        }
        response.setContentType(file.getContentType());

        try {
            IOUtils.copy(new FileInputStream(file.getUrl()), response.getOutputStream());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
