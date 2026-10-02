package org.example.basketballshop.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

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

    @OneToOne(mappedBy = "iconImageInfo")
    private Badge badge;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "product_id")
    private Product product;
}
