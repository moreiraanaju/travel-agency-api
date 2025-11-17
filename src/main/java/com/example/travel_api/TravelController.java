package com.example.travel_api;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
class TravelController {
    
    private final TravelRepository repository;

    TravelController(TravelRepository repository) {
        this.repository = repository;
    }


    // Agregate root

    
    //tag::get-aggregate-root[]
    // Retornar todos os destinos disponíveis
    @GetMapping("/travels")
    List<Travel> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    // Create a new travel destination
    @PostMapping("/travels")
    Travel newTravel(@RequestBody Travel newTravel) {
        return repository.save(newTravel);
    }


    // Single item


    // Search a travel destiny by name or location
    @GetMapping("/travels/search")
    public List<Travel> searchTravel(
        @RequestParam(required = false) String destinationName,
        @RequestParam(required = false) String location) {

        if(destinationName == null && location == null) {
            return repository.findAll();
        }
        
        // Combined search
        return repository
            .findByDestinationNameContainingIgnoreCaseOrLocationContainingIgnoreCase(
                destinationName, location
            );
    }

    // View detailed information about a specific travel destination 
    @GetMapping("/travels/{id}")
    public Travel viewTravel(@PathVariable Long id) {

        return repository.findById(id)
            .orElseThrow(() -> new TravelNotFoundException(id));
    }
   
    

    // Update all the information about a travel destination
    @PutMapping("/travels/{id}")  
    Travel replaceTravel(@RequestBody Travel newTravel, @PathVariable Long id) { 
    //(@RequestBody Travel newTravel -> dados enviados pelo cliente (JSON)   ||   @PathVariable Long id -> O ID que o cliente passou na url

        return repository.findById(id) // search for an existing travel destination with this id
            .map(travel -> {
                travel.setDestinationName(newTravel.getDestinationName());
                travel.setDate(newTravel.getDate());
                travel.setLocation(newTravel.getLocation());
                return repository.save(travel);
            })
            .orElseGet(() -> {
                return repository.save(newTravel);
            });

    }

    // Update the rating note of a travel destination
    @PatchMapping("/travels/{id}/rating")
    public Travel updateRating(@RequestBody RatingRequest request, @PathVariable Long id) {
        
        return repository.findById(id)    // search for an existing travel destination with this id
            .map(travel -> {
                travel.registerRating(request.getRating());
                return repository.save(travel);
            })
            .orElseThrow(() -> new RuntimeException("Destino de viagem não encontrado"));
    }

    // Delete a specific travel destination
    @DeleteMapping("/travels/{id}") 
    void deleteTravel(@PathVariable Long id) {
        repository.deleteById(id);
    }
    

}
