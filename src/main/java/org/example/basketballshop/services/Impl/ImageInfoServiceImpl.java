package org.example.basketballshop.services.Impl;


import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.example.basketballshop.models.ImageInfo;
import org.example.basketballshop.repositories.ImageInfoRepository;
import org.example.basketballshop.services.ImageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ImageInfoServiceImpl implements ImageInfoService {

    private final S3Client s3Client;
    private final ImageInfoRepository imageInfoRepository;
    private final String bucketName;

    public ImageInfoServiceImpl(
            S3Client s3Client,
            ImageInfoRepository imageInfoRepository,
            @Value("${storage.s3.bucket}") String bucketName) {
        this.s3Client = s3Client;
        this.imageInfoRepository = imageInfoRepository;
        this.bucketName = bucketName;
    }

    @PostConstruct
    private void initBucket() {
        try {
            s3Client.headBucket(HeadBucketRequest.builder().bucket(bucketName).build());
        } catch (NoSuchBucketException e) {
            s3Client.createBucket(CreateBucketRequest.builder().bucket(bucketName).build());
        }
    }


    @Override
    public ImageInfo saveImage(MultipartFile uploadFile) {
        if (uploadFile == null || uploadFile.isEmpty()) {
            return null;
        }

        String imageStorageName = UUID.randomUUID() + "_" + uploadFile.getOriginalFilename();

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(imageStorageName)
                    .contentType(uploadFile.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(uploadFile.getInputStream(), uploadFile.getSize()));
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке файла в S3-хранилище", e);
        }

        ImageInfo image = ImageInfo.builder()
                .contentType(uploadFile.getContentType())
                .size(uploadFile.getSize())
                .originalName(uploadFile.getOriginalFilename())
                .storageName(imageStorageName)
                .build();

        return imageInfoRepository.save(image);
    }

    @Override
    public List<ImageInfo> saveImages(MultipartFile[] uploadFiles) {
        List<ImageInfo> imageInfoList = new ArrayList<>();

        if (uploadFiles != null) {
            for (MultipartFile file : uploadFiles) {
                if (!file.isEmpty()) {
                    ImageInfo image = saveImage(file);
                    imageInfoList.add(image);
                }
            }
        }
        return imageInfoList;
    }
}
