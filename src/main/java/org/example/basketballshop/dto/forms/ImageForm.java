package org.example.basketballshop.dto.forms;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageForm {
    private String originalName;
    private String storageName;
    private String contentType;
    private long size;
    private String url;
}