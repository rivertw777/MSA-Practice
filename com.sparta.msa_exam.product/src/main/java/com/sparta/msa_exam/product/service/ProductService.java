package com.sparta.msa_exam.product.service;

import com.sparta.msa_exam.product.dto.CreateProductRequest;
import com.sparta.msa_exam.product.dto.CreateProductResponse;
import com.sparta.msa_exam.product.dto.ProductResponse;
import com.sparta.msa_exam.product.entity.Product;
import com.sparta.msa_exam.product.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @CacheEvict(cacheNames = "products", allEntries = true)
    public CreateProductResponse createProduct(CreateProductRequest request) {
        Product product = new Product(request.name(), request.supply_price());
        productRepository.save(product);
        return new CreateProductResponse("상품 생성 완료");
    }

    @Cacheable(cacheNames = "products")
    public List<ProductResponse> getProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private ProductResponse toResponse(Product product){
        return new ProductResponse(
                product.getProduct_id(),
                product.getName(),
                product.getSupply_price()
        );
    }

}
