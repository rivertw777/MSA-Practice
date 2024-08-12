package com.sparta.msa_exam.product.dto;

import java.io.Serializable;

public record ProductResponse(Long product_id, String name, Integer supply_price) implements Serializable {
}
