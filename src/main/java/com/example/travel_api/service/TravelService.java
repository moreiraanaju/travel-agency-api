package com.example.travel_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.travel_api.Travel;
import com.example.travel_api.TravelNotFoundException;
import com.example.travel_api.TravelRepository;

@Service
public class TravelService {

    private final TravelRepository repository;

    public TravelService(TravelRepository repository) {
        this.repository = repository;

    }


    // métodos (antes estaam no controller)

    public List<Travel> findAll() {
        return repository.findAll();
    }


    public List<Travel> search(String destinationName, String location) {
        if (destinationName == null && location == null) {
            return repository.findAll();
        }

        return repository.findByDestinationNameContainingIgnoreCaseOrLocationContainingIgnoreCase(
            destinationName, location
        );
    }


    public Travel findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new TravelNotFoundException(id));
    }


    public Travel create(Travel newTravel) {
        return repository.save(newTravel);
    }


    public Travel replace(Long id, Travel newTravel) {
    return repository.findById(id)
        .map(travel -> {
            travel.setDestinationName(newTravel.getDestinationName());
            travel.setDate(newTravel.getDate());
            travel.setLocation(newTravel.getLocation());
            return repository.save(travel);
        })
        .orElseGet(() -> {
            newTravel.setId(id);
            return repository.save(newTravel);
  
        });
    }

    public Travel updateRating(Long id, int rating) {
        Travel travel = repository.findById(id)
            .orElseThrow(() -> new TravelNotFoundException(id));

        travel.registerRating(rating);
        return repository.save(travel);
    }


    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new TravelNotFoundException(id);
        }
        repository.deleteById(id);
    }


   



}
