package com.example.quiznurlanmoldabekov.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CalculationResponse {
    private BigDecimal cost;
    private BigDecimal distance;
}
