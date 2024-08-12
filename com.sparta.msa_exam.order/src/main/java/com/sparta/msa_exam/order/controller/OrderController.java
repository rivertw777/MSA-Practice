package com.sparta.msa_exam.order.controller;

import com.sparta.msa_exam.order.dto.AddProductRequest;
import com.sparta.msa_exam.order.dto.AddProductResponse;
import com.sparta.msa_exam.order.dto.CreateOrderRequest;
import com.sparta.msa_exam.order.dto.CreateOrderResponse;
import com.sparta.msa_exam.order.dto.OrderResponse;
import com.sparta.msa_exam.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderController {

    @Value("${server.port}")
    private String serverPort;

    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderRequest request){
        CreateOrderResponse response = orderService.createOrder(request);
        return ResponseEntity.ok()
                .header("Server-Port", serverPort)
                .body(response);
    }

    @PutMapping("/order/{orderId}")
    public ResponseEntity<AddProductResponse> addProduct(@PathVariable("orderId") Long orderId,
                                                         @RequestBody AddProductRequest request) {
        AddProductResponse response = orderService.addProduct(orderId, request);
        return ResponseEntity.ok()
                .header("Server-Port", serverPort)
                .body(response);
    }

    @GetMapping("order/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable("orderId") Long orderId) {
        OrderResponse response = orderService.getOrder(orderId);
        return ResponseEntity.ok()
                .header("Server-Port", serverPort)
                .body(response);
    }

}
