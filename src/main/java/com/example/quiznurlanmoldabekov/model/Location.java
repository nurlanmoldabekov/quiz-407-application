package com.example.quiznurlanmoldabekov.model;

import lombok.Data;

import java.util.List;

@Data
public class Location {
    private String name;
    private List<Route> routes;
}
