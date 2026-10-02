package org.example.basketballshop.repositories;

import org.example.basketballshop.models.ImageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageInfoRepository extends JpaRepository<ImageInfo, Long> {
    ImageInfo findByStorageName(String fileName);
}
