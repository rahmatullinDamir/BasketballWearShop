package org.example.basketballshop.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "image_info")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalName;
    private String storageName;
    private String contentType;
    private long size;
    private String url;

    @OneToOne(mappedBy = "iconImageInfo")
    private Badge badge;


    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
