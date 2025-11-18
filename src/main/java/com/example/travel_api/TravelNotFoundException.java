package com.example.travel_api;

public class TravelNotFoundException extends RuntimeException {
    public TravelNotFoundException(Long id) {
        super("Não foi possível encontrar a viagem" + id);
    }
}
