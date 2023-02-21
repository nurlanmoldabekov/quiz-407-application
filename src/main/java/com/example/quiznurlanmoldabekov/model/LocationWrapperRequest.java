package com.example.quiznurlanmoldabekov.model;

import lombok.Data;

import java.util.Map;

@Data
public class LocationWrapperRequest {
    private Map<Long, Location> locations;
}
