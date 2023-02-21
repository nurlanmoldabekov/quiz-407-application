package com.example.quiznurlanmoldabekov.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
@Data
public class RouteConfig {
    @Value("${route.cost-per-km}")
    private BigDecimal costPerKm;
}
