package com.sparta.msa_exam.product.controller;

import com.sparta.msa_exam.product.dto.CreateProductRequest;
import com.sparta.msa_exam.product.dto.CreateProductResponse;
import com.sparta.msa_exam.product.dto.ProductResponse;
import com.sparta.msa_exam.product.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final Environment environment;

    @PostMapping("/products")
    public ResponseEntity<CreateProductResponse> createProduct(@RequestBody CreateProductRequest request) {
        CreateProductResponse response = productService.createProduct(request);
        return ResponseEntity.ok()
                .header("Server-Port", getServerPort())
                .body(response);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getProducts() {
        List<ProductResponse> responses = productService.getProducts();
        return ResponseEntity.ok()
                .header("Server-Port", getServerPort())
                .body(responses);
    }

    private String getServerPort() {
        return environment.getProperty("server.port");
    }

}

