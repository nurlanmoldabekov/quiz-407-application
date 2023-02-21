package com.example.quiznurlanmoldabekov.router;

import com.example.quiznurlanmoldabekov.config.RouteConfig;
import com.example.quiznurlanmoldabekov.exception.NotFoundException;
import com.example.quiznurlanmoldabekov.model.CalculationResponse;
import com.example.quiznurlanmoldabekov.model.Location;
import com.example.quiznurlanmoldabekov.model.LocationWrapperRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RouterService {
    private Map<Long, Location> locations;
    private Map<String, Long> locationIds;
    private final RouteConfig config;

    public RouterService(ObjectMapper mapper, RouteConfig config) throws IOException {
        ClassPathResource staticDataResource = new ClassPathResource("interchanges.json");
        updateLocations(mapper.readValue(staticDataResource.getInputStream(), LocationWrapperRequest.class));
        this.config = config;
    }

    public void updateLocations(LocationWrapperRequest locationWrapper) {
        locations = locationWrapper.getLocations();
        locationIds = locations.entrySet().stream().collect(Collectors.toMap(
                e -> e.getValue().getName(),
                Map.Entry::getKey)
        );
    }

    public CalculationResponse calculate(String from, String to) {
        validateRoutes(from, to);
        var fromId = locationIds.get(from);
        var toId = locationIds.get(to);
        var distance = BigDecimal.ZERO;
        distance = calc(fromId, toId, distance);
        if (!distance.equals(BigDecimal.ZERO)) {
            distance = distance.setScale(3, RoundingMode.HALF_UP);
        }
        var finalCost = distance.multiply(config.getCostPerKm()).setScale(2, RoundingMode.HALF_UP);
        return CalculationResponse.builder().distance(distance).cost(finalCost).build();
    }

    private BigDecimal calc(Long fromId, Long toId, BigDecimal distance) {
        var location = locations.get(fromId);
        var routes = location.getRoutes();
        if (toId > fromId) {
            var route = routes.get(0);
            return calc(route.getToId(), toId, distance.add(route.getDistance()));
        } else if (toId < fromId) {
            var route = routes.get(1);
            return calc(route.getToId(), toId, distance.add(route.getDistance()));
        } else {
            return distance;
        }
    }

    private void validateRoutes(String from, String to) {
        if (!locationIds.containsKey(from)) {
            String message = String.format("Route %s was not found", from);
            throw new NotFoundException(message);
        }
        if (!locationIds.containsKey(to)) {
            String message = String.format("Route %s was not found", to);
            throw new NotFoundException(message);
        }
    }


}
