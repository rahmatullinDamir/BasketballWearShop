package org.example.basketballshop.mapper;

import lombok.RequiredArgsConstructor;
import org.example.basketballshop.dto.ImageInfoDto;
import org.example.basketballshop.models.ImageInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class ImageInfoMapper {

    @Value("${storage.s3.bucket}")
    private String bucketName;
    @Value("${storage.s3.endpoint}")
    private String endpoint;

    public ImageInfoDto in(ImageInfo imageInfo) {
        if (imageInfo == null) {
            return null;
        }

        String fullUrl = String.format("%s/%s/%s", endpoint, bucketName, imageInfo.getStorageName());

        return ImageInfoDto.builder()
                .id(imageInfo.getId())
                .originalName(imageInfo.getOriginalName())
                .imageUrl(fullUrl)
                .build();
    }

    public List<ImageInfoDto> from(List<ImageInfo> imageInfoList) {
        return imageInfoList.stream().map(this::in).toList();
    }


}
