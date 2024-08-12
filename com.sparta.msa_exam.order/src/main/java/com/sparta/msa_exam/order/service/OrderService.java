package com.sparta.msa_exam.order.service;

import com.sparta.msa_exam.order.client.ProductClient;
import com.sparta.msa_exam.order.dto.AddProductRequest;
import com.sparta.msa_exam.order.dto.AddProductResponse;
import com.sparta.msa_exam.order.dto.CreateOrderRequest;
import com.sparta.msa_exam.order.dto.CreateOrderResponse;
import com.sparta.msa_exam.order.dto.OrderResponse;
import com.sparta.msa_exam.order.dto.Product;
import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.entity.OrderProduct;
import com.sparta.msa_exam.order.repository.OrderRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    public CreateOrderResponse createOrder(CreateOrderRequest request) {
        Order order = new Order(request.name());
        orderRepository.save(order);
        return new CreateOrderResponse("주문 생성 완료");
    }

    @Transactional
    public AddProductResponse addProduct(Long orderId, AddProductRequest request) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            return new AddProductResponse("해당하는 주문이 없습니다.");
        }

        List<Product> products = productClient.getProducts();
        for (Product product : products) {
            if ( product.product_id().equals(request.productId())) {
                OrderProduct orderProduct = new OrderProduct(order.get(), product.product_id());
                order.get().addOrderProduct(orderProduct);
                return new AddProductResponse("상품 추가 완료");
            }
        }
        return new AddProductResponse("해당하는 상품이 없습니다.");
    }

    public OrderResponse getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 주문이 없습니다."));
        return toResponse(order);
    }

    private OrderResponse toResponse(Order order) {
        List<OrderProduct> orderProducts = order.getProduct_ids();
        for (OrderProduct orderProduct : orderProducts) {
            System.out.println(orderProduct.getProduct_id());
        }

        List<Long> productIds = order.getProduct_ids().stream()
                .map(OrderProduct::getProduct_id)
                .collect(Collectors.toList());
        return new OrderResponse(
                order.getOrder_id(),
                productIds
        );
    }

}
