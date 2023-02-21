package com.example.quiznurlanmoldabekov.controller;

import com.example.quiznurlanmoldabekov.model.BaseResponse;
import com.example.quiznurlanmoldabekov.model.CalculationResponse;
import com.example.quiznurlanmoldabekov.model.LocationWrapperRequest;
import com.example.quiznurlanmoldabekov.router.RouterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/route")
@RequiredArgsConstructor
public class RouteRestController {

    private final RouterService service;

    @GetMapping("/calc")
    public ResponseEntity<CalculationResponse> getRouteCost(@RequestParam("from") String from,
                                                            @RequestParam("to") String to) {
        return ResponseEntity.ok(service.calculate(from, to));
    }

    @PostMapping("/update-locations")
    public ResponseEntity<BaseResponse> updateLocations(@RequestBody LocationWrapperRequest locationWrapper) {
        service.updateLocations(locationWrapper);
        return ResponseEntity.ok(BaseResponse.builder().message("Updated").build());
    }
}
