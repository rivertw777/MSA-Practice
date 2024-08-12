package com.sparta.msa_exam.order.dto;

import java.io.Serializable;
import java.util.List;

public record OrderResponse(Long order_id, List<Long> product_ids) implements Serializable {
}
