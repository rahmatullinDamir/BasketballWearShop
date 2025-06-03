package org.example.basketballshop.Services.Impl;

import org.example.basketballshop.DTO.ProductDto;
import org.example.basketballshop.Models.CartItem;
import org.example.basketballshop.Models.ImageInfo;
import org.example.basketballshop.Models.Product;
import org.example.basketballshop.DTO.Forms.ProductForm;
import org.example.basketballshop.Models.ProductSize;
import org.example.basketballshop.Repositories.ImageInfoRepository;
import org.example.basketballshop.Repositories.ProductRepository;
import org.example.basketballshop.Services.ImageInfoService;
import org.example.basketballshop.Services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ImageInfoRepository imageInfoRepository;
    @Autowired
    private ImageInfoService imageInfoService;

    @Override
    public Product getProductById(Long id) {
        try {
            logger.info("Attempting to retrieve product with ID: {}", id);
            return productRepository.findById(id)
                    .orElseThrow(() -> {
                        logger.error("Product not found with ID: {}", id);
                        return new RuntimeException("Продукт не найден: " + id);
                    });
        } catch (Exception e) {
            logger.error("Error retrieving product with ID: {}", id, e);
            throw e;
        }
    }

    @Override
    public List<ProductDto> getAllProductsWithDiscount(int discount) {
        try {
            logger.info("Retrieving all products with discount: {}%", discount);
            List<ProductDto> productDtos = ProductDto.from(productRepository.findAll());
            
            if (discount != 0) {
                logger.info("Applying {}% discount to {} products", discount, productDtos.size());
                productDtos.forEach(productDto -> {
                    BigDecimal deduction = productDto.getPrice()
                            .multiply(BigDecimal.valueOf(discount)
                                    .divide(BigDecimal.valueOf(100)));
                    productDto.setDiscount(discount);
                    productDto.setDiscountPrice(productDto.getPrice().subtract(deduction));
                });
            }
            
            logger.info("Successfully retrieved and processed {} products", productDtos.size());
            return productDtos;
        } catch (Exception e) {
            logger.error("Error retrieving products with discount", e);
            throw e;
        }
    }

    @Override
    public List<CartItem> getCartProductsWithDiscount(int discount, List<CartItem> items) {
        try {
            logger.info("Applying {}% discount to {} cart items", discount, items.size());
            
            if (discount != 0) {
                items.forEach(cartItem -> {
                    BigDecimal originalPrice = cartItem.getProduct().getPrice();
                    BigDecimal deduction = originalPrice
                            .multiply(BigDecimal.valueOf(discount)
                                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
                    
                    Product discountedProduct = new Product();
                    discountedProduct.setId(cartItem.getProduct().getId());
                    discountedProduct.setName(cartItem.getProduct().getName());
                    discountedProduct.setDescription(cartItem.getProduct().getDescription());
                    discountedProduct.setImages(cartItem.getProduct().getImages());
                    discountedProduct.setSizes(cartItem.getProduct().getSizes());
                    discountedProduct.setPrice(originalPrice.subtract(deduction).setScale(2, RoundingMode.HALF_UP));
                    
                    cartItem.setProduct(discountedProduct);
                    logger.debug("Applied discount to product: {}, new price: {}", 
                            discountedProduct.getId(), discountedProduct.getPrice());
                });
            }
            
            logger.info("Successfully applied discount to cart items");
            return items;
        } catch (Exception e) {
            logger.error("Error applying discount to cart items", e);
            throw e;
        }
    }

    @Override
    public boolean saveProduct(ProductForm productForm) {
        try {
            logger.info("Starting to save new product: {}", productForm.getName());
            
            MultipartFile[] images = productForm.getImages();
            logger.info("Processing {} images for product", images.length);
            
            List<ImageInfo> imageInfoList = imageInfoService.saveImages(images);
            
            Product product = Product.builder()
                    .name(productForm.getName())
                    .description(productForm.getDescription())
                    .price(productForm.getPrice())
                    .build();
            
            logger.info("Adding sizes to product");
            for (String sizeStr : productForm.getSizesInput()) {
                ProductSize productSize = new ProductSize();
                productSize.setSize(sizeStr);
                productSize.setStock(20);
                productSize.setPrice(product.getPrice());
                productSize.setProduct(product);
                product.getSizes().add(productSize);
            }
            
            imageInfoList.forEach(imageInfo -> imageInfo.setProduct(product));
            product.setImages(imageInfoList);
            
            productRepository.save(product);
            logger.info("Successfully saved product with ID: {}", product.getId());
            return true;
        } catch (DataAccessException e) {
            logger.error("Database error while saving product", e);
            throw new RuntimeException("Error saving product to database", e);
        } catch (Exception e) {
            logger.error("Unexpected error while saving product", e);
            throw new RuntimeException("Error saving product", e);
        }
    }

    @Override
    public void deleteProductById(Long id) {
        try {
            logger.info("Attempting to delete product with ID: {}", id);
            
            Product product = getProductById(id);
            logger.info("Found product to delete: {}", product.getName());
            
            logger.info("Deleting {} associated images", product.getImages().size());
            product.getImages().forEach(image -> {
                try {
                    imageInfoRepository.delete(image);
                } catch (Exception e) {
                    logger.error("Error deleting image for product {}", id, e);
                    throw e;
                }
            });
            
            productRepository.deleteById(id);
            logger.info("Successfully deleted product with ID: {}", id);
        } catch (Exception e) {
            logger.error("Error deleting product with ID: {}", id, e);
            throw e;
        }
    }

    @Override
    public List<Map<String, Object>> getPopularSizesByProduct() {
        try {
            logger.info("Retrieving popular sizes by product");
            List<Map<String, Object>> result = productRepository.findPopularSizesByProduct();
            logger.info("Successfully retrieved popular sizes for {} products", result.size());
            return result;
        } catch (Exception e) {
            logger.error("Error retrieving popular sizes by product", e);
            throw e;
        }
    }
}