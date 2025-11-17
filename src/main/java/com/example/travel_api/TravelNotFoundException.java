package com.example.travel_api;

class TravelNotFoundException extends RuntimeException {
    TravelNotFoundException(Long id) {
        super("Não foi possível encontrar a viagem" + id);
    }
}
