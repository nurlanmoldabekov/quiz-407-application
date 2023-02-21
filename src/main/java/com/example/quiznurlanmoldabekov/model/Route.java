package com.example.quiznurlanmoldabekov.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Route {
    private Long toId;
    private BigDecimal distance;
}