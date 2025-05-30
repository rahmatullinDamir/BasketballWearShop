package org.example.basketballshop.Services.Impl;

import org.example.basketballshop.DTO.ProductDto;
import org.example.basketballshop.Models.ImageInfo;
import org.example.basketballshop.Models.Product;
import org.example.basketballshop.DTO.Forms.ProductForm;
import org.example.basketballshop.Repositories.ImageInfoRepository;
import org.example.basketballshop.Repositories.ProductRepository;
import org.example.basketballshop.Services.ImageInfoService;
import org.example.basketballshop.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ImageInfoRepository imageInfoRepository;
    @Autowired
    private ImageInfoService imageInfoService;

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Product not found with id: " + id));
    }


    @Override
    public List<ProductDto> getAllProductsWithDiscount(int discount) {
        List<ProductDto> productDtos = ProductDto.from(productRepository.findAll());
        if (discount != 0) {
            productDtos.forEach(productDto -> {
                BigDecimal deduction = productDto.getPrice().
                        multiply(BigDecimal.valueOf(discount)
                                .divide(BigDecimal.valueOf(100)));
                productDto.setDiscount(discount);
                productDto.setDiscountPrice(productDto.getPrice().subtract(deduction));
            });
        }

        return productDtos;
    }

    @Override
    public boolean saveProduct(ProductForm productForm) {
        MultipartFile[] images = productForm.getImages();

        List<ImageInfo> imageInfoList = imageInfoService.saveImages(images);

        Product product = Product.builder()
                .name(productForm.getName())
                .description(productForm.getDescription())
                .sizes(productForm.getSizesInput())
                .price(productForm.getPrice())
                .build();


        imageInfoList.forEach(imageInfo -> imageInfo.setProduct(product));

        product.setImages(imageInfoList);

        productRepository.save(product);
        return true;
    }

    @Override
    public void deleteProductById(Long id) {
        Product product = getProductById(id);

        product.getImages().forEach(image -> {
            imageInfoRepository.delete(image);
        });

        productRepository.deleteById(id);
    }

//    @Override
//    public Product addImagesToProduct(Long productId, List<ImageInfo> imageInfos) {
//        Product product = getProductById(productId);
//
//        for (ImageInfo info : imageInfos) {
//            ImageInfo savedInfo = imageInfoRepository.save(info);
//
//            ProductImage productImage = new ProductImage();
//            productImage.setImageInfo(savedInfo);
//            productImage.setProduct(product);
//            productImage.setMain(true); // первое — главное
//
//            product.getImages().add(productImage);
//        }
//
//        return productRepository.save(product);
//    }

//    @Override
//    public void deleteImageById(Long imageId) {
//        ProductImage image = productImageRepository.findById(imageId)
//                .orElseThrow(() -> new RuntimeException("Image not found"));
//
//        ImageInfo info = image.getImageInfo();
//
//        productImageRepository.delete(image);
//        imageInfoRepository.delete(info);
//    }
//
//    @Override
//    public void setMainImage(Long productId, Long imageId) {
//        Product product = getProductById(productId);
//
//        for (ProductImage image : product.getImages()) {
//            image.setMain(image.getId().equals(imageId));
//        }
//
//        productRepository.save(product);
//    }

    @Override
    public List<Product> searchProductsByName(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }

    @Override
    public List<Product> filterBySize(String size) {
        return productRepository.findBySizesContaining(size);
    }
}