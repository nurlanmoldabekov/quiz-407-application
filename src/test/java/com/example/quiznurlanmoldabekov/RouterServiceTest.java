package com.example.quiznurlanmoldabekov;

import com.example.quiznurlanmoldabekov.config.RouteConfig;
import com.example.quiznurlanmoldabekov.exception.NotFoundException;
import com.example.quiznurlanmoldabekov.router.RouterService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Nurlan
 */
class RouterServiceTest {
    @Mock
    static RouteConfig config;
    static RouterService service;

    @BeforeAll
    public static void init() throws IOException {
        config = mock(RouteConfig.class);
        when(config.getCostPerKm()).thenReturn(BigDecimal.valueOf(0.25));
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        service = new RouterService(mapper, config);
    }

    @Test
    void testPositiveAscendingOne() {
        var res = service.calculate("QEW", "Bronte Road");
        Assertions.assertEquals(BigDecimal.valueOf(14.062), res.getDistance());
        Assertions.assertEquals(BigDecimal.valueOf(3.52), res.getCost());
    }

    @Test
    void testPositiveAscendingTwo() {
        var res = service.calculate("QEW", "Salem Road");
        Assertions.assertEquals(BigDecimal.valueOf(115.277), res.getDistance());
        Assertions.assertEquals(BigDecimal.valueOf(28.82), res.getCost());
    }

    @Test
    void testPositiveDescendingOne() {
        var res = service.calculate("Bronte Road", "QEW");
        Assertions.assertEquals(BigDecimal.valueOf(14.062), res.getDistance());
        Assertions.assertEquals(BigDecimal.valueOf(3.52), res.getCost());
    }

    @Test
    void testPositiveDescendingTwo() {
        var res = service.calculate("Salem Road", "QEW");
        Assertions.assertEquals(BigDecimal.valueOf(107.964), res.getDistance());
        Assertions.assertEquals(BigDecimal.valueOf(26.99), res.getCost());
    }

    @Test
    void testPositiveSamePoints() {
        var res = service.calculate("QEW", "QEW");
        Assertions.assertEquals(BigDecimal.ZERO, res.getDistance());
        Assertions.assertEquals(BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP), res.getCost());
    }

    @Test
    void testNegativeFromNotFound() {
        Exception exception = Assertions.assertThrows(NotFoundException.class, () -> {
            service.calculate("QWE", "QEW");
        });
        String expectedMessage = "Route QWE was not found";
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testNegativeToNotFound() {
        Exception exception = Assertions.assertThrows(NotFoundException.class, () -> {
            service.calculate("QEW", "CTO");
        });
        String expectedMessage = "Route CTO was not found";
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }
}
